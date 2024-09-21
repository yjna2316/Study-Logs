package com.kopring.studylogs.springboot.bean.lifecycle.callbacks.config

import com.kopring.studylogs.springboot.bean.lifecycle.callbacks.BeanLifeCycleByCustomMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.kopring.studylogs.springboot.bean.lifecycle.callbacks")
class BeanLifeCycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    fun beanLifeCycleCustomMethod(): BeanLifeCycleByCustomMethod {
        return BeanLifeCycleByCustomMethod()
    }
}
