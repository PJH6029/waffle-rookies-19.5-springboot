package com.wafflestudio.seminar.domain.seminar.repository

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import org.springframework.data.jpa.repository.JpaRepository

interface SeminarRepository : JpaRepository<Seminar, Long?> {
    fun findAllByNameContains(name: String): List<Seminar>
    fun findAllByNameContainsOrderByCreatedAtDesc(name: String): List<Seminar>
    fun findAllByOrderByCreatedAtDesc(): List<Seminar>
}