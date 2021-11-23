package com.wafflestudio.seminar.global.common.exception

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.StringBuilder
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class CommonControllerAdvice {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    // exception from Dto validation
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun processValidationException(
        exception: MethodArgumentNotValidException
    ): ErrorResponse {
        val bindingResult = exception.bindingResult
        val stringBuilder = StringBuilder()

        bindingResult.fieldErrors.forEach { fieldError ->
            stringBuilder.append(fieldError.field).append(": ")
            stringBuilder.append(fieldError.defaultMessage)
            stringBuilder.append(", ")
        }
        logger.error(stringBuilder.toString())
        return ErrorResponse(ErrorType.INVALID_REQUEST.code, stringBuilder.toString())
    }

    // exception from required request header
    @ExceptionHandler(MissingRequestHeaderException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun processMissingRequestHeaderException(
        exception: MissingRequestHeaderException
    ): ErrorResponse {
        logger.error("Missing required request headers. " + exception.message)
        return ErrorResponse(ErrorType.INVALID_REQUEST.code, exception.message)
    }

    // validation 단계에서 custom annotation을 통해 잡는게 맞는지, 여기서 잡는게 맞는지?? -> 둘 다!
    // exceptions from db validation
    @ExceptionHandler(DataIntegrityViolationException::class, ConstraintViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    // SQLIntegrityConstraint~을 스프림 내부(아마 dispatcherServlet)에서 미리 잡아서 던져주기에, 바로 exception을 잡을 수 없음
    fun processDBValidationException(
        exception: Exception
    ): ErrorResponse {
        logger.error("DB validation. " + exception.message)
        // get err msg from jdbc.spi.SqlExceptionHelper -> exception.mostSpecificCause
        return ErrorResponse(ErrorType.INVALID_REQUEST.code, exception.message ?: "")
    }
}
