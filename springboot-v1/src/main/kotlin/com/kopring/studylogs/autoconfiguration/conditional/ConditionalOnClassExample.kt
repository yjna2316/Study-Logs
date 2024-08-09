package com.kopring.studylogs.autoconfiguration.conditional

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class KlassA

@Configuration
class ConditionalOnClassConfig {

    @Bean
    @ConditionalOnClass(KlassA::class)
    fun conditionalOnClassA(): String {
        println("##### conditionalOnClassA is created ##### ")
        return "conditionalOnClassA"
    }

    @Bean
    @ConditionalOnClass(name = ["com.kopring.studylogs.autoconfiguration.conditional.KlassB"])
    fun conditionalOnClassB(): String {
        println("##### conditionalOnClassB is created ##### ")
        return "conditionalOnClassB"
    }

    @Bean
    @ConditionalOnMissingClass("com.kopring.studylogs.autoconfiguration.conditional.KlassB")
    fun conditionalOnMissingB(): String {
        println("##### conditionalOnMissingB is created ##### ")
        return "conditionalOnMissingB"
    }
}


