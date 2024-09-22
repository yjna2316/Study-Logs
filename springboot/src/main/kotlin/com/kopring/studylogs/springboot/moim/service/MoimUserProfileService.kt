package com.kopring.studylogs.springboot.moim.service

import com.kopring.studylogs.springboot.moim.domain.Moim
import com.kopring.studylogs.springboot.moim.domain.UserProfile
import com.kopring.studylogs.springboot.moim.repository.MoimRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class MoimUserProfileService(
    @Value("\${api.url.user:http://localhost:9090/user/profile}") val url: String,
    private val moimRepository: MoimRepository,
) {
    private val log = LoggerFactory.getLogger(MoimUserProfileService::class.java)
    @CircuitBreaker(name = "userProfileCircuitBreaker", fallbackMethod = "getUserProfileFallback")
    fun listByUser(userId: String): List<Moim> {
        return try {
            // 최초 30초 동안, 에러 응답을 수신한다.
            val requestUrl = "$url/$userId"

            val reference: ParameterizedTypeReference<UserProfile> = object : ParameterizedTypeReference<UserProfile>() {}

            val response: ResponseEntity<UserProfile> = RestTemplate().exchange(requestUrl, HttpMethod.GET, null, reference)

            val userProfile: UserProfile = response.body ?: return  emptyList()

            val listMoim = moimRepository.findAllByHostNickname(userProfile.nickname)

            log.info("circuit breaker is [CLOSED]")
            listMoim
        } catch (ex: Exception) {
            log.error("listByUser error: {}", ex.message, ex)
            emptyList()
        }
    }

    // Circuit Breaker가 OPEN 상태일 때 들어온 요청에게 대체 응답 제공
    fun getUserProfileFallback(userId: String, throwable: Throwable): UserProfile {
        log.info("circuit breaker is [OPEN], userId=$userId", throwable)
        throw RuntimeException("일시적인 문제입니다. 잠시 후 다시 시도해 주세요", throwable)
    }
}
