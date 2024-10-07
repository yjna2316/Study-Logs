package com.kopring.studylogs.springboot.moim.service

import com.kopring.studylogs.springboot.moim.domain.Moim
import com.kopring.studylogs.springboot.moim.outbound.UserProfileClient
import com.kopring.studylogs.springboot.moim.repository.MoimRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MoimUserProfileService(
    private val moimRepository: MoimRepository,
    private val userProfileClient: UserProfileClient,
) {
    private val log = LoggerFactory.getLogger(MoimUserProfileService::class.java)

    @CircuitBreaker(name = "userProfileCircuitBreaker", fallbackMethod = "getUserProfileFallback")
    fun listByUser(userId: String): List<Moim> {
        return try {
            val userProfile = userProfileClient.getUserProfile(userId) ?: return emptyList()
            val listMoim = moimRepository.findAllByHostNickname(userProfile.nickname)
            listMoim
        } catch (ex: Exception) {
            throw ex
        }
    }

    /**
     * 써킷 상태가 OPEN 인 경우, FallBack 메소드를 통해 사용자에게 사전에 정의된 안내메시지를 내려준다.
     */
    fun getUserProfileFallback(userId: String, exception: Exception): List<Moim> {
        log.info("getUserProfileFallback method is called, userId=$userId - {}", exception.message)
        throw RuntimeException("일시적인 문제입니다. 잠시 후 다시 시도해 주세요", exception)
    }
}
