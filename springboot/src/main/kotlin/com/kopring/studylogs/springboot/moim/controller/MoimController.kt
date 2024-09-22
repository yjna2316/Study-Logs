package com.kopring.studylogs.springboot.moim.controller

import com.kopring.studylogs.springboot.moim.domain.Moim
import com.kopring.studylogs.springboot.moim.service.MoimService
import com.kopring.studylogs.springboot.moim.service.MoimUserProfileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MoimController(
    private val moimService: MoimService,
    private val moimUserProfileService: MoimUserProfileService,
) {
    @GetMapping("/moims")
    fun listMoim(): List<Moim> {
        return moimService.list()
    }

    @GetMapping("/moims/slow")
    fun listMoimSlow(): List<Moim> {
        return moimService.listSlow()
    }

    @GetMapping("/moims/user/{userId}")
    fun listMoimByUserIdFail(@PathVariable("userId") userId: String): List<Moim> {
        return moimUserProfileService.listByUser(userId)
    }
}
