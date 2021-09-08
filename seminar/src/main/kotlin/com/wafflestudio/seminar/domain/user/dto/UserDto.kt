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
        // TODO 여기에 NotBlank해주지 않으면, Entity에 NotBlank가 있어도 그냥 db에 들어감 -> why?
       @field:NotBlank
       var name: String? = "",

       @field:NotBlank
       @field:Email
       var email: String? = "",
    )
}