package com.wafflestudio.seminar.domain.os.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class OperatingSystem(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:NotNull
    val price: Long,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "os")
    val surveyResponses: MutableSet<SurveyResponse> = mutableSetOf()
) : BaseEntity()
