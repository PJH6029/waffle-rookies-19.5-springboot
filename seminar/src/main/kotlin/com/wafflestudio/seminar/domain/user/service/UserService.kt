package com.wafflestudio.seminar.domain.user.service

import com.wafflestudio.seminar.domain.os.model.User
import com.wafflestudio.seminar.domain.os.repository.UserRepository
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.SQLIntegrityConstraintViolationException

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun addUser(newUser: User): User {
        return userRepository.save(newUser)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
    }
}