package com.example.springboot_url_shortener;


import com.example.springboot_url_shortener.entity.UrlMapping;
import com.example.springboot_url_shortener.repository.RequestLogRepository;
import com.example.springboot_url_shortener.repository.UrlMappingRepository;
import com.example.springboot_url_shortener.service.UrlService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @Test
    void shorten_returns_short_url_and_logs() throws Exception {
        UrlMappingRepository urlRepo = mock(UrlMappingRepository.class);
        RequestLogRepository logRepo = mock(RequestLogRepository.class);

        UrlMapping saved = new UrlMapping("https://example.com");
        // set id via reflection (simulate DB-generated id)
        var idField = UrlMapping.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(saved, 125L);

        when(urlRepo.findByOriginalUrl("https://example.com")).thenReturn(Optional.empty());
        when(urlRepo.save(any(UrlMapping.class))).thenReturn(saved);

        UrlService service = new UrlService(urlRepo, logRepo);
        String shortUrl = service.shorten("https://example.com", "127.0.0.1", "http://localhost:8080");

        assertThat(shortUrl).startsWith("http://localhost:8080/");
        verify(logRepo, times(1)).save(any());
    }
}
