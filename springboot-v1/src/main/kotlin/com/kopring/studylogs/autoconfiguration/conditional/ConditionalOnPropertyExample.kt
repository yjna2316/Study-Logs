package com.kopring.studylogs.autoconfiguration.conditional

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConditionalOnPropertyExample {

    @Bean
    @ConditionalOnProperty(name = ["feature.toggle"], havingValue = "true")
    fun featureToggleBeanTrue(): String {
        return "Feature is enabled!"
    }

    @Bean
    @ConditionalOnProperty(name = ["feature.toggle"], havingValue = "false")
    fun featureToggleBeanFalse(): String {
        return "Feature is not enabled!"
    }
}