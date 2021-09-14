package com.wafflestudio.seminar.common.api

import com.wafflestudio.seminar.common.dto.CommonDto
import com.wafflestudio.seminar.common.exception.WaffleDuplicateException
import com.wafflestudio.seminar.common.exception.WaffleException
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
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
    // exception from Dto validation
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun processValidationException(
        exception: MethodArgumentNotValidException
    ): CommonDto.ErrorResponse{
        println("DEBUG: called processValidationException")
        println("DEBUG: Caught $exception")
        val bindingResult = exception.bindingResult
        val stringBuilder = StringBuilder()

        bindingResult.fieldErrors.forEach {
                fieldError ->
            stringBuilder.append(fieldError.field).append(": ")
            stringBuilder.append(fieldError.defaultMessage)
            stringBuilder.append(", ")
        }
        return CommonDto.ErrorResponse(message = stringBuilder.toString(), status = HttpStatus.BAD_REQUEST.value())
    }

    // exception from required request header
    @ExceptionHandler(MissingRequestHeaderException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun processMissingRequestHeaderException(
        exception: MissingRequestHeaderException
    ): CommonDto.ErrorResponse {
        println("DEBUG: called processMissingRequestHeaderException")
        println("DEBUG: Caught $exception")

        return CommonDto.ErrorResponse(message = exception.message, status=HttpStatus.BAD_REQUEST.value())
    }

    // validation 단계에서 custom annotation을 통해 잡는게 맞는지, 여기서 잡는게 맞는지?? -> 둘 다!
    // exceptions from db validation
    @ExceptionHandler(DataIntegrityViolationException::class, ConstraintViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    // SQLIntegrityConstraint~을 스프림 내부(아마 dispatcherServlet)에서 미리 잡아서 던져주기에, 바로 exception을 잡을 수 없음
    fun processDBValidationException(
        exception: Exception
    ): CommonDto.ErrorResponse {
        println("DEBUG: called processDBValidationException")
        println("DEBUG: Caught $exception")
        // get err msg from jdbc.spi.SqlExceptionHelper -> exception.mostSpecificCause
        return CommonDto.ErrorResponse(message = exception.message, status=HttpStatus.BAD_REQUEST.value())
    }


    // notfound, duplicate exceptions
    @ExceptionHandler(WaffleNotFoundException::class, WaffleDuplicateException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun processNotFoundException(
        exception: WaffleException
    ): CommonDto.ErrorResponse {
        println("DEBUG: called processNotFoundException")
        println("DEBUG: Caught $exception")
        return CommonDto.ErrorResponse(message = exception.message, status = HttpStatus.NOT_FOUND.value())
    }
}