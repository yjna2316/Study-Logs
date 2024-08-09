package com.kopring.autoconfiguration.conditional.config

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class ConditionalOnClassTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun `classpath에 KlassA 클래스가 있으면, conditionalOnClassA 빈이 등록된다`() {
        assertTrue(isClassPresent(CLASSPATH_KLASS_A))
        val result = applicationContext.getBean("conditionalOnClassA", String::class.java)
        result shouldBe "conditionalOnClassA"
    }

    @Test
    fun `classpath에 KlassB 클래스가 없으면, conditionalOnClassB 빈은 등록되지 않는다`() {
        assertFalse(isClassPresent(CLASSPATH_KLASS_B))
        shouldThrowExactly<NoSuchBeanDefinitionException> {
            applicationContext.getBean("conditionalOnClassB", String::class.java)
        }
    }

    @Test
    fun `classpath에 KlassB 클래스가 없으면, conditionalOnMissingB 빈이 등록된다`() {
        assertFalse(isClassPresent(CLASSPATH_KLASS_B))
        val result = applicationContext.getBean("conditionalOnMissingB", String::class.java)
        result shouldBe "conditionalOnMissingB"
    }

    private fun isClassPresent(className: String): Boolean {
        return try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    companion object {
        const val CLASSPATH_KLASS_A = "com.kopring.studylogs.autoconfiguration.conditional.KlassA"
        const val CLASSPATH_KLASS_B = "com.kopring.studylogs.autoconfiguration.conditional.KlassB"
    }
}
