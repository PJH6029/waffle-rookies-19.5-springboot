package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class SeminarParticipant(
    @ManyToOne
    @JoinColumn(name = "participant_profile_id", referencedColumnName = "id")
    val participantProfile: ParticipantProfile,

    // @ManyToOne  // TODO cascade test
    @ManyToOne(cascade = [CascadeType.ALL])
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
