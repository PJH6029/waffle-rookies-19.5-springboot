package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Seminar(
    @field:NotBlank
    var name: String,

    @field:NotNull
    @field:Positive
    var capacity: Int,

    @field:NotNull
    @field:Positive
    var count: Int,

    @field:NotBlank
    @field:Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$")
    var time: String,

    @field:NotNull
    var online: Boolean,

    @OneToMany(mappedBy = "seminar")
    val instructors: MutableSet<InstructorProfile> = mutableSetOf(),

    //@OneToMany(mappedBy = "seminar")
    @OneToMany(mappedBy = "seminar")
    val seminarParticipants: MutableSet<SeminarParticipant> = mutableSetOf()
) : BaseTimeEntity() {
    fun updatedBy(updateRequest: SeminarDto.UpdateRequest): Seminar {
        this.name = updateRequest.name ?: this.name
        this.capacity = updateRequest.capacity ?: this.capacity
        this.count = updateRequest.count ?: this.count
        this.time = updateRequest.time ?: this.time
        this.online = updateRequest.online ?: this.online
        return this
    }
}
