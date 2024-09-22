package com.kopring.studylogs.springboot.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProfileController(
    private val userProfileService: UserProfileService,
) {

    companion object {
        private val START_TIME = System.currentTimeMillis()
    }

    @GetMapping("/user/profile/{userId}")
    fun userInfo(@PathVariable("userId") userId: Int): UserProfile {
        if (System.currentTimeMillis() < START_TIME + 30000) {
            throw RuntimeException("UserProfile 장애 발생")
        }
        return userProfileService.getUserProfileData(userId)
    }
}
