package com.kopring.studylogs.springboot.user

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

@RestController
class UserProfileController(
    private val userProfileService: UserProfileService,
) {
    private val log = LoggerFactory.getLogger(UserProfileController::class.java)
    private var count = AtomicInteger(0)

    @GetMapping("/user/profile/{userId}")
    fun userInfo(@PathVariable("userId") userId: Int): UserProfile {
        val currentCount = count.incrementAndGet()
        return if (shouldFail(currentCount)) {
            log.info("UserProfile Error - currentCount: $currentCount")
            throw RuntimeException("UserProfile System Error Occurred")
        } else {
            userProfileService.getUserProfileData(userId)
        }
    }

    private fun shouldFail(currentCount: Int): Boolean {
        return currentCount in 5..10
    }
}
