package com.kopring.studylogs.springboot.bean.lifecycle.dependson

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

@Component
@DependsOn("databaseInitializer")
class UserService {

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    @PostConstruct
    fun init() {
        logger.info("[UserService] 초기화 시작")
        // 유저 관련 로직 초기화
    }
}