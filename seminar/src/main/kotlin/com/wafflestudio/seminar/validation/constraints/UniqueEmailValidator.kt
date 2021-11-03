package com.wafflestudio.seminar.validation.constraints

import com.wafflestudio.seminar.domain.user.exception.UserAlreadyExistsException
import com.wafflestudio.seminar.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class UniqueEmailValidator(val userRepository: UserRepository) : ConstraintValidator<UniqueEmail, String> {
    override fun isValid(email: String, context: ConstraintValidatorContext): Boolean {
        val isDuplicate = userRepository.existsByEmail(email)

        if (isDuplicate) {
            throw UserAlreadyExistsException()

/*            addConstraintViolation(
                context,
                "Email $email already exists",
            )*/

        }
        return !isDuplicate
    }

/*    fun addConstraintViolation(context: ConstraintValidatorContext, msg: String) {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation()
    }*/
}