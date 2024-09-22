package com.kopring.studylogs.springboot.user

import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Component

@Component
class UserProfileService {
    private val dummyUserProfiles by lazy {
        mapOf(
            1 to UserProfile(id = 1, gender = Gender.FEMALE, age = 10, nickname = "jay"),
            2 to UserProfile(id = 2, gender = Gender.MALE, age = 20, nickname = "yay"),
            3 to UserProfile(id = 3, gender = Gender.FEMALE, age = 30, nickname = "jack"),
        )
    }

    fun getUserProfileData(userId: Int): UserProfile {
        return dummyUserProfiles[userId] ?: throw BadRequestException("No user found with userId: $userId")
    }
}