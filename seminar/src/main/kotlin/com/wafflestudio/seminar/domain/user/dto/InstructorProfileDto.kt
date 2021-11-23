package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile

class InstructorProfileDto {
    data class Response(
        val id: Long,
        val name: String,
        val email: String,
        val company: String
    ) {
        constructor(instructorProfile: InstructorProfile) : this(
            id = instructorProfile.user.id,
            name = instructorProfile.user.name,
            email = instructorProfile.user.email,
            company = instructorProfile.company
        )
    }

    data class UserInfoResponse(
        val id: Long,
        val company: String,
        val year: Int?,
        val charge: SeminarDto.SimpleResponse?
    ) {
        constructor(instructorProfile: InstructorProfile) : this(
            id = instructorProfile.id,
            company = instructorProfile.company,
            year = instructorProfile.year,
            charge = instructorProfile.seminar?.let { SeminarDto.SimpleResponse(it) }
        )
    }
}
