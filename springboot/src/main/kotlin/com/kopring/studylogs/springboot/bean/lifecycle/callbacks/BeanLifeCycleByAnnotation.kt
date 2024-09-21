package com.kopring.studylogs.springboot.bean.lifecycle.callbacks

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class BeanLifeCycleByAnnotation {
    private val logger = LoggerFactory.getLogger(BeanLifeCycleByAnnotation::class.java)

    @PostConstruct
    fun initBean() {
        logger.info("Initialization method of " + this.javaClass.name)
    }

    @PreDestroy
    fun destroyBean() {
        logger.info("Destroy method of " + this.javaClass.name)
    }
}