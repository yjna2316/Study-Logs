package com.kopring.studylogs.autoconfiguration.conditional

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component class KBeanA
class KBeanB

@Configuration
class ConditionalOnMissingBeanConfig {
    @Bean
    @ConditionalOnBean(value = [KBeanA::class])
    fun conditionalOnBeanA(): String {
        println("#### conditionalOnBeanA is created ####")
        return "conditionalOnBeanA"
    }

    @Bean
    @ConditionalOnMissingBean(value = [KBeanB::class])
    fun conditionalOnMissingBeanB(): String {
        println("#### conditionalOnMissingBeanB is created ####")
        return "conditionalOnMissingBeanB"
    }
}
