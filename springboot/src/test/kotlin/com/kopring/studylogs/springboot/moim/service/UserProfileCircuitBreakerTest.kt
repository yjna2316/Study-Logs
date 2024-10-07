package com.kopring.studylogs.springboot.moim.service

import com.kopring.studylogs.springboot.moim.domain.Moim
import com.kopring.studylogs.springboot.moim.domain.UserProfile
import com.kopring.studylogs.springboot.moim.outbound.UserProfileClient
import com.kopring.studylogs.springboot.moim.repository.MoimRepository
import com.kopring.studylogs.springboot.user.Gender
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

/**
 * 1. 30초 동안 minimumNumberOfCalls(3개) 이상의 API 호출 수가 FAILED(실패율 50%)하면 OPEN 상태가 되고 fallback 메소드가 호출된다.
 * 2. 30초 동안 minimumNumberOfCalls(3개) 미만의 API 호출되었다면 실패율 50% 여도 CLOSED 상태가 유지되고 fallback 메소드는 호출되지 않는다.
 * 4. 30초 동안 5개 API 호출 수 중 2개만 FAILED(실패율 50% 미만)하면 CLOSED 상태가 유지되고 fallback 메소드는 호출되지 않는다.
 * 5. OPEN 상태에서의 FALLBACK 동작 테스트
 * 6. HALF-OPEN 상태에서의 동작 테스트
 *     1. 30초 동안 5번 호출 수가
 * 7. CLOSED 상태에서의 동작 테스트
 */
@SpringBootTest
class UserProfileCircuitBreakerTest {
    @Autowired
    private lateinit var circuitBreakerRegistry: CircuitBreakerRegistry

    @Autowired
    private lateinit var moimUserProfileService: MoimUserProfileService

    @MockBean
    private lateinit var moimRepository: MoimRepository

    @MockBean
    private lateinit var userProfileClient: UserProfileClient

    @BeforeEach
    fun init() {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileCircuitBreaker")
        circuitBreaker.reset()
    }

    @Test
    fun `정상 응답 시 Circuit Breaker는 CLOSED 상태를 유지한다`() {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileCircuitBreaker")

        val userProfile = UserProfile(id = 1, gender = Gender.FEMALE, age = 10, nickname = "jay")

        Mockito.`when`(userProfileClient.getUserProfile(Mockito.anyString())).thenReturn(userProfile)

        Mockito.`when`(moimRepository.findAllByHostNickname("jay")).thenReturn(
            listOf(
                Moim(id = 1, title = "모임1", hostNickname = "jay"),
                Moim(id = 2, title = "모임2", hostNickname = "jay")
            )
        )
        val moims = moimUserProfileService.listByUser("jay")

        assertEquals(moims.size,2)
        assertEquals(moims[0].hostNickname, "jay")

        assertEquals(State.CLOSED, circuitBreaker.state)
    }

    @Test
    fun `최소 호출 수(minimumNumberOfCalls)를 초과하고 실패율이 충족되면 CircuitBreaker가 OPEN 상태가 된다`() {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileCircuitBreaker")

        Mockito.`when`(userProfileClient.getUserProfile(Mockito.anyString())).thenThrow(RuntimeException("UserProfile 서비스 호출 실패"))

        repeat(5) {
            assertThrows<RuntimeException> {
                moimUserProfileService.listByUser("jay")
            }
        }

        assertEquals(State.OPEN, circuitBreaker.state,"최소 호출 수(minimumNumberOfCalls) 이상 실패한 경우 써킷 상태는 OPEN 상태여야 합니다.")
    }

    @Test
    fun `최소 호출 수(minimumNumberOfCalls) 미만이면 CircuitBreaker는 CLOSED 상태를 유지한다`() {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileCircuitBreaker")

        Mockito.`when`(userProfileClient.getUserProfile(Mockito.anyString())).thenThrow(RuntimeException("UserProfile 서비스 호출 실패"))

        repeat(1) {
            assertThrows<RuntimeException> {
                moimUserProfileService.listByUser("jay")
            }
        }

        assertEquals(State.CLOSED, circuitBreaker.state, "최소 호출 수(minimumNumberOfCalls)를 만족하지 못한 경우 CLOSED 상태를 유지해야합니다.")
    }

    @Test
    fun `실패율이 50% 미만이면 CircuitBreaker는 CLOSED 상태를 유지한다`() {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileCircuitBreaker")
        var callCount = 0
        Mockito.`when`(userProfileClient.getUserProfile(Mockito.anyString())).thenAnswer {
            if (callCount < 2) {
                callCount++
                throw RuntimeException("UserProfile 서비스 호출 실패")
            } else {
                UserProfile(id = 1, gender = Gender.FEMALE, age = 10, nickname = "jay")
            }
        }

        // 총 5번 호출 중 2번만 실패 발생
        repeat(10) {
            try {
                moimUserProfileService.listByUser("jay")
            } catch (ex: RuntimeException) {
                // Expected exception
            }
        }

        assertEquals(State.CLOSED, circuitBreaker.state,"실패율이 50% 미만이므로 CLOSED 상태를 유지해야합니다.")
    }
}
