package com.example.springboot_url_shortener.repository;

import com.example.springboot_url_shortener.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {}