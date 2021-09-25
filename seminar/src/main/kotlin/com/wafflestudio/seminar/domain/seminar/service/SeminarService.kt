package com.wafflestudio.seminar.domain.seminar.service

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.seminar.excpetion.AlreadyJoinedException
import com.wafflestudio.seminar.domain.seminar.excpetion.SeminarNotFoundException
import com.wafflestudio.seminar.domain.seminar.excpetion.SeminarParticipantNotFound
import com.wafflestudio.seminar.domain.seminar.excpetion.UnaffordableCapacityException
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.seminar.repository.SeminarParticipantRepository
import com.wafflestudio.seminar.domain.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.domain.user.exception.AlreadyInstructorException
import com.wafflestudio.seminar.domain.user.exception.NotAllowedUserException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.InstructorProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeminarService(
    private val seminarRepository: SeminarRepository,
    private val instructorProfileRepository: InstructorProfileRepository,
    private val seminarParticipantRepository: SeminarParticipantRepository,
) {
    fun getSeminarById(id: Long): Seminar {
        return seminarRepository.findByIdOrNull(id) ?: throw SeminarNotFoundException()
    }

    fun getSeminarsByNameContains(name: String, earliest: Boolean = true): List<Seminar> {
        return if (earliest) seminarRepository.findAllByNameContains(name) else seminarRepository.findAllByNameContainsOrderByCreatedAtDesc(name)
    }

    fun getAllSeminars(earliest: Boolean = true): List<Seminar> {
        return if (earliest) seminarRepository.findAll() else seminarRepository.findAllByOrderByCreatedAtDesc()

    }

    @Transactional
    fun updateSeminar(seminar: Seminar, updateRequest: SeminarDto.UpdateRequest): Seminar {
        val participantCount = seminarParticipantRepository.findAllBySeminar(seminar).count()
        if (updateRequest.capacity != null && updateRequest.capacity < participantCount) {
            throw UnaffordableCapacityException()
        }
        val updatedSeminar = seminar.updatedBy(updateRequest)
        return seminarRepository.save(updatedSeminar)
    }

    @Transactional
    fun createSeminar(seminarCreateRequest: SeminarDto.CreateRequest, instructorProfile: InstructorProfile): Seminar {
        val newSeminar = seminarCreateRequest.let {
            Seminar(
                it.name,
                it.capacity,
                it.count,
                it.time,
                it.online
            )
        }

        if (instructorProfile.seminar != null) {
            throw AlreadyInstructorException()
        }
        instructorProfile.seminar = newSeminar
        instructorProfileRepository.save(instructorProfile)

        return seminarRepository.save(newSeminar)
    }


    fun joinAsParticipant(participantProfile: ParticipantProfile, seminar: Seminar): ParticipantProfile {
        if (checkJoinedSeminar(participantProfile.user, seminar)) {
            throw AlreadyJoinedException()
        }

        val participantCount = seminar.seminarParticipants.count()
        if (participantCount >= seminar.capacity) {
            throw UnaffordableCapacityException("This seminar is already full")
        }

        val newSeminarParticipant = SeminarParticipant(
            participantProfile,
            seminar
        )
        return seminarParticipantRepository.save(newSeminarParticipant).participantProfile
    }

    fun joinAsInstructor(instructorProfile: InstructorProfile, seminar: Seminar): InstructorProfile {
        if (checkJoinedSeminar(instructorProfile.user, seminar)) {
            throw AlreadyJoinedException("You've joined this seminar as an instructor")
        }
        instructorProfile.seminar = seminar
        return instructorProfileRepository.save(instructorProfile)
    }

    fun dropSeminar(participantProfile: ParticipantProfile, seminar: Seminar): Seminar {
        val seminarParticipant = seminarParticipantRepository.findByParticipantProfileAndSeminar(participantProfile, seminar) ?: throw SeminarParticipantNotFound()
        return seminarParticipantRepository.save(seminarParticipant.dropped()).seminar

    }

    fun authorizeUnchargedInstructor(user: User): InstructorProfile {
        val instructorProfile = user.instructorProfile ?: throw NotAllowedUserException("You're not an instructor")
        if (instructorProfile.seminar != null) {
            throw AlreadyInstructorException()
        }
        return instructorProfile
    }

    fun authorizeInstructor(user: User): InstructorProfile {
        return user.instructorProfile ?: throw NotAllowedUserException("You're not an instructor")
    }

    fun authorizeParticipant(user: User): ParticipantProfile {
        val participantProfile = user.participantProfile ?: throw NotAllowedUserException("You're not a participant")
        if (!participantProfile.accepted) {
            throw NotAllowedUserException("You're an unaccepted participant")
        }
        return participantProfile
    }

    fun authorizeInstructorOfSeminar(user: User, seminar: Seminar): InstructorProfile {
        val instructorProfile = authorizeInstructor(user)
        if (instructorProfile !in seminar.instructors) {
            throw NotAllowedUserException("You're not in charge of this seminar")
        }
        return instructorProfile
    }

    fun authorizeParticipantOfSeminar(user: User, seminar: Seminar): ParticipantProfile {
        val participantProfile = authorizeParticipant(user)
        if (!seminarParticipantRepository.existsByParticipantProfileAndSeminar(participantProfile, seminar)) {
            throw NotAllowedUserException("You're not a participant of this seminar")
        }
        return participantProfile
    }

    fun checkInstructorOfSeminar(user: User, seminar: Seminar): Boolean {
        val instructorProfile = user.instructorProfile ?: return false
        return checkInstructorOfSeminar(instructorProfile, seminar)
    }

    fun checkInstructorOfSeminar(instructorProfile: InstructorProfile, seminar: Seminar): Boolean {
        if (instructorProfile in seminar.instructors) {
            return true
        }
        return false
    }

    fun checkParticipantOfSeminar(user: User, seminar: Seminar): Boolean {
        val participantProfile = user.participantProfile ?: return false
        return checkParticipantOfSeminar(participantProfile, seminar)
    }

    fun checkParticipantOfSeminar(participantProfile: ParticipantProfile, seminar: Seminar): Boolean {
        return seminarParticipantRepository.existsByParticipantProfileAndSeminar(participantProfile, seminar)
    }

    fun checkJoinedSeminar(user: User, seminar: Seminar): Boolean {
        val joinedAsParticipant = checkParticipantOfSeminar(user, seminar)
        val joinedAsInstructor = checkInstructorOfSeminar(user, seminar)

        return joinedAsInstructor || joinedAsParticipant
    }
}