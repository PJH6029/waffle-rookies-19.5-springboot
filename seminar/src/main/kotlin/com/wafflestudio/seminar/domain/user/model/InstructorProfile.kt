package com.wafflestudio.seminar.domain.user.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.user.dto.UserDto
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(name = "seminar_instructor_profile")
class InstructorProfile(
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @field:NotNull
    var company: String,

    @field:Positive
    var year: Int? = null,

    // @ManyToOne
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "seminar_id", referencedColumnName = "id")
    var seminar: Seminar? = null,
) : BaseTimeEntity() {
    fun updatedBy(updateRequest: UserDto.UpdateRequest): InstructorProfile {
        this.company = updateRequest.company
        this.year = updateRequest.year
        return this
    }
}
