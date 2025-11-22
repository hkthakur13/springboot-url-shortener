package com.example.springboot_url_shortener.controller;

import com.example.springboot_url_shortener.dto.ShortenRequest;
import com.example.springboot_url_shortener.entity.RequestLog;
import com.example.springboot_url_shortener.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

@Operation(
        summary = "Generate a short Base62 URL",
        description = "Takes a long URL and returns a shortened Base62 URL."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Short URL generated successfully",
                content = @Content(mediaType = "application/json", examples = {
                        @ExampleObject(value = """
                                {
                                  "shortUrl": "http://localhost:8080/b"
                                }
                                """)
                })
        ),
        @ApiResponse(responseCode = "400", description = "Invalid URL provided",
                content = @Content(mediaType = "application/json"))
})
@PostMapping("/api/shorten")
public ResponseEntity<?> shorten(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "URL to shorten",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject("""
                        {
                          "url": "https://google.com/search?q=test"
                        }
                        """)
                )
        )
        @RequestBody ShortenRequest request,
        HttpServletRequest servletRequest
) {

    String scheme = servletRequest.getScheme();
    String host = servletRequest.getHeader("Host");
    String base = scheme + "://" + host;

    String shortUrl = urlService.shorten(request.url(), servletRequest.getRemoteAddr(), base);

    return ResponseEntity.ok().body(
            Map.of("shortUrl", shortUrl)
    );
}

    @Operation(summary = "Get all submitted request logs")
    @GetMapping("/api/logs")
    public List<RequestLog> getLogs() {
        return urlService.getLogs();
    }

    @Operation(summary = "Clear request logs")
    @DeleteMapping("/api/logs")
    public ResponseEntity<Void> clearLogs() {
        urlService.clearLogs();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Redirect short code to original URL")
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(
            @Parameter(description = "Base62 short code", required = true)
            @PathVariable("code") String code
    ) {
        return urlService.findOriginal(code)
                .map(original -> ResponseEntity.status(302).location(URI.create(original)).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

@Operation(
        summary = "Resolve short code to original URL (no redirect)",
        description = "Useful for Swagger testing. Returns the long URL for a Base62 code."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Original URL found",
                content = @Content(mediaType = "application/json", examples = {
                        @ExampleObject(value = """
                                {
                                  "originalUrl": "https://google.com"
                                }
                                """)
                })
        ),
        @ApiResponse(responseCode = "404", description = "Short code not found",
                content = @Content(mediaType = "application/json"))
})
@GetMapping("/api/resolve/{code}")
public ResponseEntity<?> resolve(
        @Parameter(
                name = "code",
                description = "Base62 short code generated earlier",
                example = "b",
                required = true
        )
        @PathVariable("code") String code
) {

    return urlService.findOriginal(code)
            .map(original -> ResponseEntity.ok(Map.of("originalUrl", original)))
            .orElseGet(() -> ResponseEntity.notFound().build());
}

}

