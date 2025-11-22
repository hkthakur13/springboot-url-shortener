package com.example.springboot_url_shortener.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "request_log")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createdAt;

    @Column(length = 2048)
    private String originalUrl;

    private String clientIp;

    public RequestLog() {}

    public RequestLog(Instant createdAt, String originalUrl, String clientIp) {
        this.createdAt = createdAt;
        this.originalUrl = originalUrl;
        this.clientIp = clientIp;
    }

    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public String getOriginalUrl() { return originalUrl; }
    public String getClientIp() { return clientIp; }
}
