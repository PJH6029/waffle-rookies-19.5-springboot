package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.user.dto.ParticipantProfileDto
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.AlreadyParticipantException
import com.wafflestudio.seminar.domain.user.exception.InvalidSignInException
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.repository.InstructorProfileRepository
import com.wafflestudio.seminar.domain.user.repository.ParticipantProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    // 마지막으로 수정된 친구를 저장?

    @Transactional
    fun signup(signupRequest: UserDto.SignupRequest): User {
        val encodedPassword = passwordEncoder.encode(signupRequest.password)

        val newUser = User(signupRequest.email, signupRequest.name, encodedPassword, roles = "ROLE_" + signupRequest.role.uppercase() + ",")
        if (signupRequest.role == "participant") {
            newUser.participantProfile = ParticipantProfile(
                newUser,
                signupRequest.university,
                signupRequest.accepted
            )

        } else {
            newUser.instructorProfile = InstructorProfile(
                newUser,
                signupRequest.company,
                signupRequest.year
            )
        }
        return userRepository.save(newUser)
    }

    fun signin(signinRequest: UserDto.SigninRequest): User {
        return userRepository.findByEmailAndPassword(signinRequest.email, passwordEncoder.encode(signinRequest.password))
            ?: throw InvalidSignInException()
    }

    fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }

    @Transactional
    fun updateUser(user: User, updateRequest: UserDto.UpdateRequest): User {
        if (user.participantProfile != null) {
            // TODO why can't smart cast??
            user.participantProfile = user.participantProfile?.updatedBy(updateRequest) ?: throw UserNotFoundException("Participant Not Found")
        }

        if (user.instructorProfile != null) {
            user.instructorProfile = user.instructorProfile?.updatedBy(updateRequest) ?: throw UserNotFoundException("Instructor Not Found")
        }
        return userRepository.save(user)
    }

    @Transactional
    fun registerAsParticipant(user: User, registerRequest: ParticipantProfileDto.RegisterRequest): User {
        if(user.participantProfile != null) {
            throw AlreadyParticipantException()
        }

        user.participantProfile = ParticipantProfile(
            user,
            registerRequest.university,
            registerRequest.accepted
        )

        return userRepository.save(user)
    }
}