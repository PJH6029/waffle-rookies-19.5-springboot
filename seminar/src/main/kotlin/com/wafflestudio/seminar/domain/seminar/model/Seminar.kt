package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Seminar(
    @field:NotBlank
    val name: String,

    @field:NotNull
    @field:Positive
    val capacity: Int,

    @field:NotNull
    @field:Positive
    val count: Int,

    @field:NotBlank
    @field:Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$")
    val time: String,

    @field:NotNull
    val online: Boolean,

    @OneToMany(mappedBy = "seminar")
    val instructors: MutableSet<InstructorProfile> = mutableSetOf(),

    @OneToMany(mappedBy = "seminar")
    val seminarParticipants: MutableSet<SeminarParticipant> = mutableSetOf()
) : BaseTimeEntity() {
    fun updatedBy(updateRequest: SeminarDto.UpdateRequest): Seminar {
        return updateRequest.let {
            Seminar(
                it.name ?: this.name,
                it.capacity ?: this.capacity,
                it.count ?: this.count,
                it.time ?: this.time,
                it.online ?: this.online
            )
        }
    }
}