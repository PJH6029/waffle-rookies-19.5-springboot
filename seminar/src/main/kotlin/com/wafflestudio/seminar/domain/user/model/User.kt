package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "seminar_user")
class User(
    @Column(unique = true)
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val password: String,

    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "user")
    val surveyResponses: MutableSet<SurveyResponse>? = mutableSetOf(),

    @Column
    @field:NotNull
    val roles: String = "",

    ) : BaseEntity() {
    @PreRemove
    fun preRemove() {
        surveyResponses?.forEach { it.user = null }
    }
}

