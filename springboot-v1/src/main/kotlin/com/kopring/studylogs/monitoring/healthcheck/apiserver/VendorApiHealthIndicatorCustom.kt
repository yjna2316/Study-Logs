package com.kopring.studylogs.monitoring.healthcheck.apiserver

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.context.annotation.Profile
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.concurrent.atomic.AtomicInteger

@Profile("api-server")
@Component("customVendorHealth")
class VendorApiHealthIndicatorCustom(
    @Value("\${api.url:http://localhost:9090/vendor/healthy}") val url: String
): HealthIndicator {
    private val logger = LoggerFactory.getLogger(VendorApiHealthIndicatorCustom::class.java)

    val maxRetries = 5
    val restTemplate = RestTemplate()
    val failureCount = AtomicInteger(0)

    @Scheduled(fixedRate = 3000)
    override fun health(): Health {
        var health = HealthStatusGenerator.unknown(0)
        repeat(maxRetries) {
            try {
                val lastResponse: ResponseEntity<Map<String, String>> = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<Map<String, String>>() {})

                if (lastResponse.statusCode.is2xxSuccessful) {
                    failureCount.set(0)
                    health = HealthStatusGenerator.up(lastResponse)
                    return@repeat
                } else {
                    failureCount.incrementAndGet()
                    health = HealthStatusGenerator.down(lastResponse)
                }

                Thread.sleep(1000L)

            } catch (ex: Exception) {
                logger.error("Health Check failed: ${ex.message}", ex)
                health = HealthStatusGenerator.downWithException(null, ex)
            }
        }
        return health
    }
}
