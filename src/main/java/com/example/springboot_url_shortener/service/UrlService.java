package com.example.springboot_url_shortener.service;

import com.example.springboot_url_shortener.entity.RequestLog;
import com.example.springboot_url_shortener.entity.UrlMapping;
import com.example.springboot_url_shortener.repository.RequestLogRepository;
import com.example.springboot_url_shortener.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlMappingRepository urlMappingRepository;
    private final RequestLogRepository requestLogRepository;

    public UrlService(UrlMappingRepository urlMappingRepository, RequestLogRepository requestLogRepository) {
        this.urlMappingRepository = urlMappingRepository;
        this.requestLogRepository = requestLogRepository;
    }

    @Transactional
    public String shorten(String originalUrl, String clientIp, String baseHost) {
        // log request
        requestLogRepository.save(new RequestLog(Instant.now(), originalUrl, clientIp));

        // reuse existing mapping if present
        Optional<UrlMapping> existing = urlMappingRepository.findByOriginalUrl(originalUrl);
        UrlMapping mapping = existing.orElseGet(() -> urlMappingRepository.save(new UrlMapping(originalUrl)));

        String code = Base62.encode(mapping.getId());
        return baseHost + "/" + code;
    }

    public List<RequestLog> getLogs() {
        return requestLogRepository.findAll();
    }

    public void clearLogs() {
        requestLogRepository.deleteAll();
    }

    public Optional<String> findOriginal(String code) {
        try {
            long id = Base62.decode(code);
            return urlMappingRepository.findById(id).map(UrlMapping::getOriginalUrl);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}