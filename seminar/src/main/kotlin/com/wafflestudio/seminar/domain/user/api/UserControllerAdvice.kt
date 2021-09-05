package com.wafflestudio.seminar.domain.user.api

import com.wafflestudio.seminar.domain.os.model.User
import com.wafflestudio.seminar.domain.user.dto.UserDto
import com.wafflestudio.seminar.domain.user.exception.UserNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.lang.StringBuilder

@RestControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(BindException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun userValidationError(
        exception: BindException
    ): UserDto.ErrorResponse{
        val bindingResult = exception.bindingResult
        val stringBuilder = StringBuilder()

        bindingResult.fieldErrors.forEach {
            fieldError ->
            stringBuilder.append(fieldError.field).append(": ")
            stringBuilder.append(fieldError.defaultMessage)
            stringBuilder.append(", ")
        }
        return UserDto.ErrorResponse(message = stringBuilder.toString(), status = HttpStatus.BAD_REQUEST.value())
    }

    // TODO ConstraintsViolationException ??
    // TODO return type: ResponseEntity
    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    // SQLIntegrityConstraint~을 스프림 내부(아마 dispatcherServlet)에서 미리 잡아서 던져주기에, 바로 exception을 잡을 수 없음
    fun processIntegrityException(
        exception: DataIntegrityViolationException
    ): UserDto.ErrorResponse {
        // TODO get err msg from jdbc.spi.SqlExceptionHelper
        return UserDto.ErrorResponse(message = exception.message, status=HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun processNotFoundException(
        exception: UserNotFoundException
    ): UserDto.ErrorResponse {
        return UserDto.ErrorResponse(message = exception.message, status = HttpStatus.NOT_FOUND.value())
    }
}