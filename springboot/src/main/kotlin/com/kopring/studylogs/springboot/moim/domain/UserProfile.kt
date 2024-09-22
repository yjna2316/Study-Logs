package com.kopring.studylogs.springboot.moim.domain

data class UserProfile(
    val id: Int,
    val nickname: String,
    val gender: Gender,
    val age: Int,
)
