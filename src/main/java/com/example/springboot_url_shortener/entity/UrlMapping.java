package com.example.springboot_url_shortener.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048, unique = true)
    private String originalUrl;

    public UrlMapping() {}

    public UrlMapping(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getId() { return id; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
}
