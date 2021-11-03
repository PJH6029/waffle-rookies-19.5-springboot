package com.wafflestudio.seminar.domain.user.repository

import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantProfileRepository: JpaRepository<ParticipantProfile, Long?> {

}
