package com.wafflestudio.seminar.validation.constraints

import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class SeminarRoleValidator : ConstraintValidator<SeminarRole, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        val valid = value in arrayOf("participant", "instructor")

        if (!valid) {
            addConstraintViolation(
                context,
                "Role should be either participant or instructor",
            )
        }

        return valid
    }

    fun addConstraintViolation(context: ConstraintValidatorContext, msg: String) {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation()
    }
}
