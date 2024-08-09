package com.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootV1Application

fun main(args: Array<String>) {
    runApplication<SpringBootV1Application>(*args)
}
