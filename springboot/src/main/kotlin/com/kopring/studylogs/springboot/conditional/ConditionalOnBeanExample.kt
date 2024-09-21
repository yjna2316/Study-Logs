package com.kopring.studylogs.springboot.conditional

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component class KBeanA
class KBeanB

@Configuration
class ConditionalOnMissingBeanConfig {
    private val log = LoggerFactory.getLogger(ConditionalOnMissingBeanConfig::class.java)

    @Bean
    @ConditionalOnBean(value = [KBeanA::class])
    fun conditionalOnBeanA(): String {
        log.info("#### conditionalOnBeanA is created ####")
        return "conditionalOnBeanA"
    }

    @Bean
    @ConditionalOnMissingBean(value = [KBeanB::class])
    fun conditionalOnMissingBeanB(): String {
        log.info("#### conditionalOnMissingBeanB is created ####")
        return "conditionalOnMissingBeanB"
    }
}
