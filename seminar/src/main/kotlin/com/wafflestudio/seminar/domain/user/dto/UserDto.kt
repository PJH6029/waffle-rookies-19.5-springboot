package com.wafflestudio.seminar.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.validation.constraints.SeminarRole
import com.wafflestudio.seminar.validation.constraints.UniqueEmail
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class UserDto {
    data class Response(
        val id: Long,
        val name: String,
        val email: String,
        @JsonProperty("date_joined")
        val dateJoined: LocalDateTime?,
        @JsonProperty("participant_profile")
        val participantProfile: ParticipantProfileDto.UserInfoResponse?,
        @JsonProperty("instructor_profile")
        val instructorProfile: InstructorProfileDto.UserInfoResponse?
    ) {
        constructor(user: User) : this(
            id = user.id,
            name = user.name,
            email = user.email,
            dateJoined = user.createdAt,
            participantProfile = user.participantProfile?.let { ParticipantProfileDto.UserInfoResponse(it) },
            instructorProfile = user.instructorProfile?.let { InstructorProfileDto.UserInfoResponse(it) }
        )
    }

    data class SignupRequest(
        @field:UniqueEmail
        @field:NotBlank
        @field:Email
        val email: String,

        @field:NotBlank
        val name: String,

        @field:NotBlank
        val password: String,

        @field:NotBlank
        @SeminarRole
        val role: String,

        // for participant
        val university: String = "",
        val accepted: Boolean = true,

        // for instructor
        val company: String = "",
        @field:Positive
        val year: Int? = null,
    )

    data class SigninRequest(
        val email: String,
        val password: String,
    )

    // TODO signup과 통합할 수 없을까?
    data class UpdateRequest(
        // for participant
        val university: String = "",

        // for instructor
        val company: String = "",
        @field:Positive
        val year: Int? = null,
    )

    data class JoinRequest(
        @field:NotBlank
        @SeminarRole
        val role: String,
    )
}
