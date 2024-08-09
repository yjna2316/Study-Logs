package com.kopring.autoconfiguration.conditional.config

import io.kotest.assertions.throwables.shouldThrowExactly
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["feature.toggle=true"])
class ConditionalOnPropertyExampleTest {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun conditionalOnPropertyMeetsCondition() {
        assertTrue(applicationContext.containsBean("featureToggleBeanTrue"))
        val bean: String = applicationContext.getBean("featureToggleBeanTrue", String::class.java)
        assertEquals("Feature is enabled!", bean)
    }

    @Test
    fun conditionalOnPropertyNotMeetsCondition() {
        assertFalse(applicationContext.containsBean("featureToggleBeanFalse"))
        shouldThrowExactly<NoSuchBeanDefinitionException> {
            applicationContext.getBean("featureToggleBeanFalse", String::class.java)
        }
    }
}