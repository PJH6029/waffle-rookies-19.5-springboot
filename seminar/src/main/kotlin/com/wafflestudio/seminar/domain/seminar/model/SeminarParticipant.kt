package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SeminarParticipant(
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "participant_profile_id", referencedColumnName = "id")
    val participantProfile: ParticipantProfile,

    @ManyToOne(cascade = [CascadeType.ALL])  // TODO cascade test
    @JoinColumn(name = "seminar_id", referencedColumnName = "id")
    val seminar: Seminar,

    val joinedAt: LocalDateTime = LocalDateTime.now(),
    var isActive: Boolean = true,
    var droppedAt: LocalDateTime? = null,
) : BaseTimeEntity() {
    fun dropped(): SeminarParticipant {
        this.isActive = false
        this.droppedAt = LocalDateTime.now()
        return this
    }
}