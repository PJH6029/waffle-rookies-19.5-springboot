package com.wafflestudio.seminar.domain.user.dto

import org.springframework.http.HttpStatus
import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserDto {
    data class Response(
        var id: Long? = 0,
        var name: String? = "",
        var email: String? = "",
    )

    data class CreateRequest(
       @field:NotBlank
       var name: String? = "",

       @field:NotBlank
       @field:Email
       var email: String? = "",
    )
}