package com.kopring.studylogs.springboot.moim.service

import com.kopring.studylogs.springboot.moim.domain.Moim
import com.kopring.studylogs.springboot.moim.repository.MoimRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MoimService(
    private val moimRepository: MoimRepository,
) {
    private val log = LoggerFactory.getLogger(MoimService::class.java)

    fun list(): List<Moim> {
        return moimRepository.findAll()
    }

    @CircuitBreaker(name = "userProfileSlowCallCircuitBreaker", fallbackMethod = "getMoimSlowCallFallback")
    fun listSlow(): List<Moim> {
        return moimRepository.findAllBySlowCall()
    }

    fun getMoimSlowCallFallback(throwable: Throwable): List<Moim> {
        log.info("circuit breaker is [OPEN]", throwable)
        throw RuntimeException("일시적인 문제입니다. 잠시 후 다시 시도해 주세요", throwable)
    }
}