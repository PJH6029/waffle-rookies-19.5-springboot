package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.common.dto.CommonDto
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.StringBuilder

@RestControllerAdvice(basePackageClasses = [SurveyResponseController::class])
class SurveyResponseControllerAdvice {
}