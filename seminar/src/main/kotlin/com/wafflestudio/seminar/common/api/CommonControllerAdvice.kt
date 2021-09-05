package com.wafflestudio.seminar.common.api

import com.wafflestudio.seminar.common.dto.CommonDto
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonControllerAdvice {
    @ExceptionHandler(WaffleNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun processNotFoundException(
        exception: WaffleNotFoundException
    ): CommonDto.ErrorResponse {
        return CommonDto.ErrorResponse(message = exception.message, status = HttpStatus.NOT_FOUND.value())
    }
}