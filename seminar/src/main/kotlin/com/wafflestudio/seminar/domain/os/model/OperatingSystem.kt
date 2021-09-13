package com.wafflestudio.seminar.domain.os.model

import com.wafflestudio.seminar.domain.model.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class OperatingSystem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    var name: String? = null,

    @field:NotBlank
    var description: String? = null,

    @field:NotNull
    var price: Long? = null,
): BaseEntity()
