package com.example.springboot_url_shortener.repository;

import com.example.springboot_url_shortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
}
