package com.kopring.studylogs.springboot.conditional

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class KlassA

@Configuration
class ConditionalOnClassConfig {
    private val log = LoggerFactory.getLogger(ConditionalOnClassConfig::class.java)

    @Bean
    @ConditionalOnClass(KlassA::class)
    fun conditionalOnClassA(): String {
        log.info("##### conditionalOnClassA is created #####")
        return "conditionalOnClassA"
    }

    @Bean
    @ConditionalOnClass(name = ["com.kopring.studylogs.autoconfiguration.conditional.KlassB"])
    fun conditionalOnClassB(): String {
        log.info("##### conditionalOnClassB is created #####")
        return "conditionalOnClassB"
    }

    @Bean
    @ConditionalOnMissingClass("com.kopring.studylogs.autoconfiguration.conditional.KlassB")
    fun conditionalOnMissingB(): String {
        log.info("##### conditionalOnMissingB is created #####")
        return "conditionalOnMissingB"
    }
}


