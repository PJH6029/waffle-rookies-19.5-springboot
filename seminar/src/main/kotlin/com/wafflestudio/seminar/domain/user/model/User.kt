package com.wafflestudio.seminar.domain.os.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    var name: String? = null,

    @field:NotBlank
    @field:Email
    @Column(unique = true)
    var email: String? = null,

    @OneToMany(mappedBy = "user")
    var responses: MutableSet<SurveyResponse>? = HashSet()

): BaseEntity() {
    @PreRemove
    fun preRemove() {
        responses?.forEach { it.user = null }
    }
}
