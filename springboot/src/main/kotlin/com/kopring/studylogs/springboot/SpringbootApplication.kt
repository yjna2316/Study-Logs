package com.kopring.studylogs.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SpringbootApplication

fun main(args: Array<String>) {
    runApplication<SpringbootApplication>(*args)
}
