package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.user.dto.UserDto
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val password: String,

    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "user")
    val surveyResponses: MutableSet<SurveyResponse> = mutableSetOf(),

    @field:NotNull
    val roles: String = "",

    @OneToOne(mappedBy = "user")
    val participantProfile: ParticipantProfile? = null,

    @OneToOne(mappedBy = "user")
    val instructorProfile: InstructorProfile? = null,



    ) : BaseTimeEntity() {
    @PreRemove
    fun preRemove() {
        surveyResponses.forEach { it.user = null }
    }
}

