package com.wafflestudio.seminar.domain.os.api

import com.wafflestudio.seminar.common.dto.CommonDto
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [OperatingSystemController::class])
class OperatingSystemControllerAdvice {
}