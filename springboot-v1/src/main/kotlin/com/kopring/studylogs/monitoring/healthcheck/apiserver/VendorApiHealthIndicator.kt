package com.kopring.studylogs.monitoring.healthcheck.apiserver

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.context.annotation.Profile
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * http://localhost:8080/actuator/health/{path}
 * http://localhost:8080/actuator/health/vendorHealth
 */
@Profile("api-server")
@Component("vendorHealth")
class VendorApiHealthIndicator(@Value("\${api.url:http://localhost:9090/vendor/healthy}") val url: String) : HealthIndicator {

    @Scheduled(fixedRate = 5000)
    override fun health(): Health {
        return try {
            val reference: ParameterizedTypeReference<Map<String, String>> = object : ParameterizedTypeReference<Map<String, String>>() {}

            val result: ResponseEntity<Map<String, String>> = RestTemplate().exchange(url, HttpMethod.GET, null, reference)

            if (result.statusCode.is2xxSuccessful) {
                Health.up().withDetails(result.body).build()

            } else {
                Health.down().withDetails(result.body).build()
            }

        } catch (e: RestClientException) {
            Health.down().withException(e).build()
        }
    }
}