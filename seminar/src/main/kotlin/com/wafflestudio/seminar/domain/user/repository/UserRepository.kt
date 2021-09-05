package com.wafflestudio.seminar.domain.os.repository

import com.wafflestudio.seminar.domain.os.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long?> {
    // fun findByNameEquals(name: String): User?
}