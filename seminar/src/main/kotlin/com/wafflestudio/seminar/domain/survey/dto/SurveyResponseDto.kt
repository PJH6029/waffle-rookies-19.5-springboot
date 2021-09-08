package com.wafflestudio.seminar.domain.survey.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.os.model.User
import com.wafflestudio.seminar.domain.user.dto.UserDto
import org.springframework.boot.context.properties.bind.Name
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SurveyResponseDto {
    data class Response(
        var id: Long? = 0,
        var user: UserDto.Response? =null,
        var os: OperatingSystem? = null,
        var springExp: Int = 0,
        var rdbExp: Int = 0,
        var programmingExp: Int = 0,
        var major: String? = "",
        var grade: String? = "",
        var backendReason: String? = "",
        var waffleReason: String? = "",
        var somethingToSay: String? = "",
        var timestamp: LocalDateTime? = null
    )

    // TODO: value로 parsable하지 않은 string이 들어간 경우? -> typeMismatch
    data class CreateRequest(
        @field:NotBlank
        var os: String? = "",
        // var os: String = "",

        // TODO customize property name
        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var springExp: Int? = null, // name이 같아야 mapping이 됨

        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var rdbExp: Int? = null,

        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var programmingExp: Int? = null,

        // TODO blank인데 db에 들어감??
        var major: String? = "",
        var grade: String? = "",
        var backendReason: String? = "",
        var waffleReason: String? = "",
        var somethingToSay: String? = "",
    )

    data class ModifyRequest(
        var something: String? = ""
        // 예시 - 지우고 새로 생성
    )
}
