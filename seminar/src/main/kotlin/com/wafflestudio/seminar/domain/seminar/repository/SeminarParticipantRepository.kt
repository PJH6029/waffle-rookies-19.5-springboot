package com.wafflestudio.seminar.domain.seminar.repository

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.data.jpa.repository.JpaRepository

interface SeminarParticipantRepository : JpaRepository<SeminarParticipant, Long?>{
    fun findByParticipantProfileAndSeminar(participantProfile: ParticipantProfile, seminar: Seminar): SeminarParticipant?
    fun findAllBySeminar(seminar: Seminar): List<SeminarParticipant>
    fun findAllByParticipantProfile(participantProfile: ParticipantProfile): List<SeminarParticipant>
    fun existsByParticipantProfileAndSeminar(participantProfile: ParticipantProfile, seminar: Seminar): Boolean
}