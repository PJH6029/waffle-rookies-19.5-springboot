package com.wafflestudio.seminar.validation.constraints

import com.wafflestudio.seminar.domain.os.repository.UserRepository
import org.springframework.stereotype.Component
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmail (
    val message: String = "Email should be unique",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@Component
class UniqueEmailValidator(val userRepository: UserRepository) : ConstraintValidator<UniqueEmail, String>{
    override fun isValid(email: String?, context: ConstraintValidatorContext): Boolean {
        val isDuplicate = userRepository.existsUserByEmail(email)

        if (isDuplicate) {

            addConstraintViolation(
                context,
                "Email $email already exists",
            )

        }
        return !isDuplicate
    }

    fun addConstraintViolation(context: ConstraintValidatorContext, msg: String) {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation()
    }
}