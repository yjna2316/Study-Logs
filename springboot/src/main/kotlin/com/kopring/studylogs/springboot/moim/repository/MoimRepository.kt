package com.kopring.studylogs.springboot.moim.repository

import com.kopring.studylogs.springboot.moim.domain.Moim
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MoimRepository {
    companion object {
        private val START_TIME = System.currentTimeMillis()
    }
    private val log = LoggerFactory.getLogger(MoimRepository::class.java)

    private val moims: List<Moim> = listOf(
        Moim(id = 1, title = "원데이 메이크업 모임", hostNickname = "jay"),
        Moim(id = 2, title = "굿모닝 요가 모임", hostNickname = "jay"),
        Moim(id = 3, title = "굿모닝 쿠킹 모임", hostNickname = "yay"),
        Moim(id = 4, title = "헬창들의 모임", hostNickname = "jack")
    )

    fun findAll(): List<Moim> {
        return moims
    }

    fun findAllBySlowCall(): List<Moim> {
        // 처음 1분 동안 느린 응답
        if (System.currentTimeMillis() < START_TIME + 30000) {
            log.info("findAllBySlowCall request in")
            Thread.sleep(2000)
        }
        return moims
    }

    fun findAllByHostNickname(nickname: String): List<Moim> {
        return moims.filter { it.hostNickname == nickname }
    }
}
