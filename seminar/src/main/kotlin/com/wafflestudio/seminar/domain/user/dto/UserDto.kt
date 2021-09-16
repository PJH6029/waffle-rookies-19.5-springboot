package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.validation.constraints.UniqueEmail
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val name: String,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name
        )
    }

    data class SignupRequest(
        @UniqueEmail
        @field:NotBlank
        @field:Email
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String,
    )
}