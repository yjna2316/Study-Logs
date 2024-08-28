package com.kopring.studylogs.autoconfiguration.conditional

import io.kotest.core.spec.style.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

// TODO: 왜 Kotest run test 버튼이 안 보일까?
// 버전 호환성.. gradle, kotlin, jdk, java 버전 ....
@SpringBootTest
class ConditionalOnClassConfigTestNotWorking(): StringSpec() {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    init {
        "datasourceA 클래스가 존재할때만 databaseConditionalOnClass 빈이 존재한다" {
            if (isClassPresent(" com.kopring.studylogs.autoconfiguration.conditional.service.DataSourceA")) {
                applicationContext.containsBean("databaseConditionalOnClass")
            }
        }
        "datasourceB 클래스가 존재하지않으면 databaseConditionalOnClass 빈은 존재하지 않는다" {
            if (isClassPresent("com.kopring.studylogs.autoconfiguration.conditional.service.DataSourceB")) {
                applicationContext.containsBean("databaseConditionalOnClass")
            }
        }
    }

    private fun isClassPresent(className: String): Boolean {
        return try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}
