package com.kopring.studylogs.monitoring.healthcheck.apiserver

import org.springframework.boot.actuate.health.Health
import org.springframework.http.ResponseEntity

object HealthStatusGenerator {

    fun up(responseBody: ResponseEntity<Map<String, String>>?): Health {
        return Health.up().withDetails(responseBody?.body).build()
    }

    fun down(responseBody: ResponseEntity<Map<String, String>>?): Health {
        return Health.down().withDetails(responseBody?.body).build()
    }

    fun downWithException(responseBody: ResponseEntity<Map<String, String>>?, ex: Exception): Health {
        return Health.down().withDetails(responseBody?.body).withException(ex).build()
    }

    fun unknown(failureCount: Int): Health {
        return Health.unknown().withDetail("failureCount", failureCount).build()
    }
}
