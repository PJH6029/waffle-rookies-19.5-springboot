package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.validation.constraints.UniqueEmail
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserDto {
    data class Response(
        var id: Long? = 0,
        var name: String? = "",
        var email: String? = "",
    )

    data class CreateRequest(
       @field:NotBlank
       var name: String? = "",

       @UniqueEmail
       @field:NotBlank
       @field:Email
       var email: String? = "",
    )
}