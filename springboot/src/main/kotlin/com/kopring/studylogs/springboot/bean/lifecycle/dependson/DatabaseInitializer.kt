package com.kopring.studylogs.springboot.bean.lifecycle.dependson

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer {

    private val logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)

    @PostConstruct
    fun init() {
        logger.info("[DatabaseInitializer] 초기화 시작 database connection and loading initial data...")
        // 데이터베이스 초기화 로직
    }
}