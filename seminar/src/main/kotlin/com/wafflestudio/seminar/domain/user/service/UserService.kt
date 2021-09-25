package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.dto.ParticipantProfileDto
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.AlreadyParticipantException
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorProfileRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val participantProfileRepository: ParticipantProfileRepository,
    private val instructorProfileRepository: InstructorProfileRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signup(signupRequest: UserDto.SignupRequest): User {
        val encodedPassword = passwordEncoder.encode(signupRequest.password)

        val newUser = User(signupRequest.email, signupRequest.name, encodedPassword, roles = "ROLE_" + signupRequest.role.uppercase() + ",")
        if (signupRequest.role == "participant") {
            participantProfileRepository.save(
                ParticipantProfile(
                    newUser,
                    signupRequest.university,
                    signupRequest.accepted
                )
            )
        } else {
            instructorProfileRepository.save(
                InstructorProfile(
                    newUser,
                    signupRequest.company,
                    signupRequest.year
                )
            )
        }
        return userRepository.save(newUser)
    }

    fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }

    @Transactional
    fun updateUser(user: User, updateRequest: UserDto.UpdateRequest): User {
        // TODO

        if (user.participantProfile != null) {
            // TODO why can't smart cast??
            val updatedParticipantProfile = user.participantProfile?.updatedBy(updateRequest) ?: throw UserNotFoundException("Participant Not Found")
            updatedParticipantProfile.university = updateRequest.university
            participantProfileRepository.save(updatedParticipantProfile)
        }
        
        
        if (user.instructorProfile != null) {
            // TODO ParticipantProfile을 var로 만들기 vs val인 채로 새로운 객체 생성하기
            val updatedInstructorProfile = user.instructorProfile?.updatedBy(updateRequest) ?: throw UserNotFoundException("Instructor Not Found")
            updatedInstructorProfile.company = updateRequest.company
            instructorProfileRepository.save(updatedInstructorProfile)
            
        }

        return user
    }

    fun registerAsParticipant(user: User, registerRequest: ParticipantProfileDto.RegisterRequest): User {
        if(user.participantProfile == null) {
            throw AlreadyParticipantException()
        }

        participantProfileRepository.save(
            ParticipantProfile(
                user,
                registerRequest.university,
                registerRequest.accepted
            )
        )

        // TODO refresh?
        return user
    }
}