package com.kopring.studylogs.springboot.moim.outbound

import com.kopring.studylogs.springboot.moim.domain.UserProfile
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UserProfileClient(
    @Value("\${api.url.user:http://localhost:9090/user/profile}") private val url: String,
    private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(UserProfileClient::class.java)

    /**
     * User 시스템을 호출하여 유저 정보(UserProfile)를 조회한다.
     */
    fun getUserProfile(userId: String): UserProfile? {
        val requestUrl = "$url/$userId"
        return try {
            val response: ResponseEntity<UserProfile> = restTemplate.exchange(requestUrl, HttpMethod.GET, null, object : ParameterizedTypeReference<UserProfile>() {})
            response.body
        } catch (ex: Exception) {
            log.error("error occurred while calling userProfileClient - {}", ex.message)
            throw ex
        }
    }
}
