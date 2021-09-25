package com.wafflestudio.seminar.validation.constraints

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [SeminarRoleValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SeminarRole (
    val message: String = "Role should be either participant or instructor",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)