package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.dto.UserDto
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_participant_profile")
class ParticipantProfile(
    @OneToOne
    @JoinColumn(name = "user")
    val user: User,

    @field:NotNull
    var university: String,

    @field:NotNull
    val accepted: Boolean,

    // @OneToMany(mappedBy = "participantProfile", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "participantProfile")
    val seminarParticipants: MutableSet<SeminarParticipant> = mutableSetOf(),
) : BaseTimeEntity() {
    fun updatedBy(updateRequest: UserDto.UpdateRequest): ParticipantProfile {
        this.university = updateRequest.university ?: this.university
        return this
    }
}
