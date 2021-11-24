package com.wafflestudio.seminar.global.common.api

import com.wafflestudio.seminar.global.common.dto.PingPongResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class LandingController {
    @GetMapping("/")
    fun pingPong(): PingPongResponse {
        return PingPongResponse("hi")
    }
}
