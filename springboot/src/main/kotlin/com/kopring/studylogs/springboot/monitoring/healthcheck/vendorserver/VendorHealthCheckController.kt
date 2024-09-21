package com.kopring.studylogs.springboot.monitoring.healthcheck.vendorserver

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VendorHealthCheckController() {

    @GetMapping("/vendor/healthy")
    fun healthy(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "OK"
            )
        )
    }

    @GetMapping("/vendor/unhealthy")
    fun unhealthy(): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                mapOf(
                    "status" to "INTERNAL_SERVER_ERROR",
                    "message" to "Vendor is not healthy"
                )
            )
    }
}