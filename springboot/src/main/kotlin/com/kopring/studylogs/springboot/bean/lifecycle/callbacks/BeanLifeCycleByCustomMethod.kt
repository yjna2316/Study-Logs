package com.kopring.studylogs.springboot.bean.lifecycle.callbacks

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class BeanLifeCycleByCustomMethod {
    private val logger = LoggerFactory.getLogger(BeanLifeCycleByCustomMethod::class.java)

    fun init() {
        logger.info("Initialization method of " + this.javaClass.name)
    }

    fun destroy() {
        logger.info("Destroy method of " + this.javaClass.name);
    }
}