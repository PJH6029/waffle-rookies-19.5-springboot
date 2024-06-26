package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.user.dto.ParticipantProfileDto
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.service.UserService
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.auth.JwtTokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/")
    fun signup(@Valid @RequestBody signupRequest: UserDto.SignupRequest): ResponseEntity<UserDto.Response> {
        val user = userService.signup(signupRequest)
        val headers = HttpHeaders()
        headers.set("Authentication", jwtTokenProvider.generateToken(user.email))
        // return ResponseEntity<UserDto.Response>(UserDto.Response(user), headers, HttpStatus.CREATED)
        return ResponseEntity.noContent().headers(headers).build()
    }

    @PostMapping("/signin/")
    // TODO doesn't work
    fun signin(@Valid @RequestBody signinRequest: UserDto.SigninRequest): ResponseEntity<UserDto.Response> {
        val user = userService.signin(signinRequest)
        val headers = HttpHeaders()
        headers.set("Authentication", jwtTokenProvider.generateToken(user.email))
        return ResponseEntity<UserDto.Response>(UserDto.Response(user), headers, HttpStatus.OK)
    }

    @GetMapping("/me/")
    @Transactional
    fun getCurrentUser(@CurrentUser user: User): UserDto.Response {
        return UserDto.Response(user)
    }

    @GetMapping("/{user_id}/")
    fun getUser(@PathVariable("user_id") id: Long): UserDto.Response {
        val user = userService.getUserById(id)
        return UserDto.Response(user)
    }

    @PutMapping("/me/")
    fun updateCurrentUser(
        @Valid @RequestBody updateRequest: UserDto.UpdateRequest,
        @CurrentUser user: User
    ): UserDto.Response {
        val updatedUser = userService.updateUser(user, updateRequest)
        return UserDto.Response(updatedUser)
    }

    @PostMapping("/participant/")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerAsParticipant(
        @Valid @RequestBody registerRequest: ParticipantProfileDto.RegisterRequest,
        @CurrentUser user: User
    ): UserDto.Response {
        return UserDto.Response(userService.registerAsParticipant(user, registerRequest))
    }
}
