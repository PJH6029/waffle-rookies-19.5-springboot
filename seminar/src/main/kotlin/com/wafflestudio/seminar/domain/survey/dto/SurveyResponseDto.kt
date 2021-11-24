package com.wafflestudio.seminar.domain.survey.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.user.dto.UserDto
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SurveyResponseDto {
    data class Response(
        val id: Long,
        val os: OperatingSystemDto.Response,
        val user: UserDto.Response?,
        val springExp: Int,
        val rdbExp: Int,
        val programmingExp: Int,
        val major: String,
        val grade: String,
        val backendReason: String?,
        val waffleReason: String?,
        val somethingToSay: String?,
        val timestamp: LocalDateTime
    ) {
        constructor(surveyResponse: SurveyResponse) : this(
            surveyResponse.id,
            OperatingSystemDto.Response(surveyResponse.os),
            surveyResponse.user?.let { UserDto.Response(it) },
            surveyResponse.springExp,
            surveyResponse.rdbExp,
            surveyResponse.programmingExp,
            surveyResponse.major,
            surveyResponse.grade,
            surveyResponse.backendReason,
            surveyResponse.waffleReason,
            surveyResponse.somethingToSay,
            surveyResponse.timestamp
        )
    }

    data class CreateRequest(
        @field:NotBlank
        var os: String = "",

        @JsonProperty("spring_exp")
        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var springExp: Int,

        @JsonProperty("rdb_exp")
        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var rdbExp: Int,

        @JsonProperty("programming_exp")
        @field:NotNull
        @field:Min(1, message = "The value must be between 1 and 5")
        @field:Max(5, message = "The value must be between 1 and 5")
        var programmingExp: Int,

        @field:NotBlank
        val major: String = "",

        @field:NotBlank
        val grade: String = "",

        val backendReason: String? = null,
        val waffleReason: String? = null,
        val somethingToSay: String? = null,
    )
}
