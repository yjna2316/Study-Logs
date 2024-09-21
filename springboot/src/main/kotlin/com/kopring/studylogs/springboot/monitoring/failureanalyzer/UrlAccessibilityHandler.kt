package com.kopring.studylogs.springboot.monitoring.failureanalyzer

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
@Profile("api-server")
class UrlAccessibilityHandler(@Value("\${api.url:http://localhost:9090/vendor/healthy}") val url: String) {

    private val log = LoggerFactory.getLogger(UrlAccessibilityHandler::class.java)

    @EventListener(classes = [ContextRefreshedEvent::class])
    fun listen() {
        try {
            val result: ResponseEntity<String> = RestTemplate().getForEntity(url, String::class.java)

            if (result.statusCode != HttpStatus.OK) {
                throw UrlNotAccessibleException(url)
            }
            log.info("URL $url is accessible.")
        } catch (e: RestClientException) {
            log.error("An error occurred while trying to access URL $url: ${e.message}")
        }
    }
}

