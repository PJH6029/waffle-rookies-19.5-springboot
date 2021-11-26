package com.wafflestudio.seminar.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime

class ParticipantProfileDto {
    data class Response(
        val id: Long,
        val name: String,
        val email: String,
        val university: String,
        @JsonProperty("joined_at")
        val joinedAt: LocalDateTime,
        @JsonProperty("is_active")
        val isActive: Boolean,
        @JsonProperty("dropped_at")
        val droppedAt: LocalDateTime?,
    ) {
        constructor(participantProfile: ParticipantProfile, seminarParticipant: SeminarParticipant) : this(
            id = participantProfile.user.id,
            name = participantProfile.user.name,
            email = participantProfile.user.email,
            university = participantProfile.university,
            joinedAt = seminarParticipant.joinedAt,
            isActive = seminarParticipant.isActive,
            droppedAt = seminarParticipant.droppedAt
        )
    }

    data class UserInfoResponse(
        val id: Long,
        val university: String,
        val accepted: Boolean,
        val seminars: List<SeminarDto.UserInfoResponse>,
    ) {
        constructor(participantProfile: ParticipantProfile) : this(
            id = participantProfile.id,
            university = participantProfile.university,
            accepted = participantProfile.accepted,
            seminars = participantProfile.seminarParticipants.map { SeminarDto.UserInfoResponse(it) }
        )
    }

    data class RegisterRequest(
        val university: String = "",
        val accepted: Boolean = true,
    )
}
