package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.stereotype.Service

@Service
class ParticipantProfileService(
    private val participantRepository: SeminarParticipantRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
) {
    fun getParticipantProfilesBySeminar(seminar: Seminar): List<ParticipantProfile> {
        val seminarParticipants = seminarParticipantRepository.findAllBySeminar(seminar)
        return seminarParticipants.map {it -> it.participantProfile}
    }

}