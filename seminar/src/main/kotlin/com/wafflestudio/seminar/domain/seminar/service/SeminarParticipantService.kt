package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.excpetion.AlreadyJoinedException
import com.wafflestudio.seminar.domain.seminar.excpetion.SeminarParticipantNotFound
import com.wafflestudio.seminar.domain.seminar.excpetion.UnaffordableCapacityException
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SeminarParticipantService(
    private val seminarParticipantRepository: SeminarParticipantRepository
) {
    fun getSeminarParticipant(participantProfile: ParticipantProfile, seminar: Seminar): SeminarParticipant {
        return seminarParticipantRepository.findByParticipantProfileAndSeminar(participantProfile, seminar) ?: throw SeminarParticipantNotFound()
    }

    fun getSeminarParticipantsBySeminar(seminar: Seminar): List<SeminarParticipant> {
        return seminarParticipantRepository.findAllBySeminar(seminar)
    }

    fun getSeminarParticipantsByParticipantByParticipantProfile(participantProfile: ParticipantProfile): List<SeminarParticipant> {
        return seminarParticipantRepository.findAllByParticipantProfile(participantProfile)
    }

}