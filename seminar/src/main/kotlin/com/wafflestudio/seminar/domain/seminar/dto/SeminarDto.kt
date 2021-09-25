package com.wafflestudio.seminar.domain.seminar.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.dto.InstructorProfileDto
import com.wafflestudio.seminar.domain.user.dto.ParticipantProfileDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.*

/*
seminar -> seminar_participants, seminar_participants.participant_profile
 */

class SeminarDto {
    data class Response(
        val id: Long,
        val name: String,
        val capacity: Int,
        val count: Int,
        val time: String,
        val online: Boolean,
        val instructors: List<InstructorProfileDto.Response>,
        val participants: List<ParticipantProfileDto.Response>,
    ) {
        constructor(seminar: Seminar) : this(
            id = seminar.id,
            name = seminar.name,
            capacity = seminar.capacity,
            count = seminar.count,
            time = seminar.time,
            online = seminar.online,
            instructors = seminar.instructors.map {InstructorProfileDto.Response(it)},
            participants = seminar.seminarParticipants.map {ParticipantProfileDto.Response(it.participantProfile, it)},
        )
    }

    data class ListResponseElement(
        val id: Long,
        val name: String,
        val instructors: List<InstructorProfileDto.Response>,
        @JsonProperty("participant_count")
        val participantCount: Int,
    ) {
        constructor(seminar: Seminar): this(
            id = seminar.id,
            name = seminar.name,
            instructors = seminar.instructors.map {InstructorProfileDto.Response(it)},
            participantCount = seminar.seminarParticipants.count { !it.isActive }
        )
    }

    data class UserInfoResponse(
        val id: Long,
        val name: String,
        @JsonProperty("joined_at")
        val joinedAt: LocalDateTime?,
        val isActive: Boolean,
        @JsonProperty("dropped_at")
        val droppedAt: LocalDateTime?,
    ) {
        constructor(seminarParticipant: SeminarParticipant): this(
            id = seminarParticipant.seminar.id,
            name = seminarParticipant.seminar.name,
            joinedAt = seminarParticipant.createdAt,
            isActive = seminarParticipant.isActive,
            droppedAt = seminarParticipant.droppedAt,
        )
    }

    data class SimpleResponse(
        val id: Long,
        val name: String,
    ) {
        constructor(seminar: Seminar): this(
            id = seminar.id,
            name = seminar.name,
        )
    }

    data class CreateRequest(
        @field:NotBlank
        val name: String = "",
        // Non-nullable type에 default value가 있지 않으면, validation에 앞서 (아마도) 생성자 단에서 NotNull 관련 에러를 던짐

        @field:NotNull
        @field:Positive
        val capacity: Int,

        @field:NotNull
        @field:Positive
        val count: Int,

        //@field:DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss a")
        // @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm", timezone = "Asia/Seoul")
        // TODO time format
        @field:NotBlank
        @field:Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$")
        val time: String = "00:00",

        // 알아서 parsing 해줌
        val online: Boolean = true
    )

    data class UpdateRequest(
        val name: String?,

        @field:Positive
        val capacity: Int?,

        @field:Positive
        val count: Int?,

        @field:Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$")
        val time: String?,

        val online: Boolean?
    )
}