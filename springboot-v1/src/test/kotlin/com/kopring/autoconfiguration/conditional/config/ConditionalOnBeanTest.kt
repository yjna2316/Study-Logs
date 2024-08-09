package com.kopring.autoconfiguration.conditional.config

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

//@SpringBootTest(classes = [ConditionalConfig::class])
@SpringBootTest
class ConditionalOnBeanTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun `KBeanA 빈이 존재하면 빈이 생성되어야 한다`() {
        assertTrue(applicationContext.containsBean("KBeanA"))
        val result = applicationContext.getBean("conditionalOnBeanA", String::class.java)
        result shouldBe "conditionalOnBeanA"
    }

    @Test
    fun `KBeanB 빈이 존재하지 않으면 conditionalOnMissingBeanB 빈이 생성된다`() {
        assertFalse(applicationContext.containsBean("KBeanB"))
        val result = applicationContext.getBean("conditionalOnMissingBeanB", String::class.java)
        result shouldBe "conditionalOnMissingBeanB"
    }
}