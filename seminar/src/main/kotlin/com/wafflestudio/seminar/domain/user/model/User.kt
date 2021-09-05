package com.wafflestudio.seminar.domain.os.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotBlank
    @NotNull
    var name: String? = null,

    @NotBlank
    @NotNull
    @Email
    @Column(unique = true)
    var email: String? = null,
)
