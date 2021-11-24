package com.wafflestudio.seminar.domain.user.repository

import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import org.springframework.data.jpa.repository.JpaRepository

interface InstructorProfileRepository : JpaRepository<InstructorProfile, Long?>
