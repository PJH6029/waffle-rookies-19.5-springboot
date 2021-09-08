package com.wafflestudio.seminar.common.api

import com.wafflestudio.seminar.common.dto.CommonDto
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.StringBuilder

@RestControllerAdvice
class CommonControllerAdvice {
    @ExceptionHandler(BindException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun userValidationError(
        exception: BindException
    ): CommonDto.ErrorResponse{
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

    // TODO ConstraintsViolationException ??
    // TODO validation 단계에서 custom annotation을 통해 잡는게 맞는지, 여기서 잡는게 맞는지??
    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    // SQLIntegrityConstraint~을 스프림 내부(아마 dispatcherServlet)에서 미리 잡아서 던져주기에, 바로 exception을 잡을 수 없음
    fun processIntegrityException(
        exception: DataIntegrityViolationException
    ): CommonDto.ErrorResponse {
        // TODO get err msg from jdbc.spi.SqlExceptionHelper
        return CommonDto.ErrorResponse(message = exception.message, status=HttpStatus.BAD_REQUEST.value())
    }


    @ExceptionHandler(WaffleNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun processNotFoundException(
        exception: WaffleNotFoundException
    ): CommonDto.ErrorResponse {
        return CommonDto.ErrorResponse(message = exception.message, status = HttpStatus.NOT_FOUND.value())
    }
}