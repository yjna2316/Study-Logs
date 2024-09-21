package com.kopring.studylogs.springboot.bean.lifecycle.callbacks

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class BeanLifeCycleByInterface: InitializingBean, DisposableBean {
    private val logger = LoggerFactory.getLogger(BeanLifeCycleByInterface::class.java)

    override fun afterPropertiesSet() {
        logger.info("Initialization method of " + this.javaClass.name)
    }

    override fun destroy() {
        logger.info("Destroy method of " + this.javaClass.name)
    }
}