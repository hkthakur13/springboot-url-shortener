package com.example.springboot_url_shortener;

import com.example.springboot_url_shortener.controller.UrlController;
import com.example.springboot_url_shortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlController.class)
public class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UrlService urlService;

    @Test
    void shortenEndpoint_returns_shortUrl() throws Exception {
        when(urlService.shorten(anyString(), anyString(), anyString())).thenReturn("http://localhost:8080/abc");

        mockMvc.perform(post("/api/shorten")
                        .contentType("application/json")
                        .content("{\"url\":\"https://ex\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/abc"));
    }
}
