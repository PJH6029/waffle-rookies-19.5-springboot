package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.survey.service.SurveyResponseService
import com.wafflestudio.seminar.domain.user.model.User
import com.wafflestudio.seminar.global.auth.CurrentUser
import com.wafflestudio.seminar.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class SurveyResponseController(
    private val surveyResponseService: SurveyResponseService,
) {
    @GetMapping("/")
    fun getSurveyResponses(@RequestParam(required = false) os: String?): ListResponse<SurveyResponseDto.Response> {
        val surveyResponses =
            if (os != null) surveyResponseService.getSurveyResponsesByOsName(os)
            else surveyResponseService.getAllSurveyResponses()
        return ListResponse(surveyResponses.map { SurveyResponseDto.Response(it) })
    }

    @GetMapping("/{id}/")
    fun getSurveyResponse(@PathVariable("id") id: Long): SurveyResponseDto.Response {
        val surveyResponse = surveyResponseService.getSurveyResponseById(id)
        return SurveyResponseDto.Response(surveyResponse)
    }

    // catches DTO validation exception on CommonControllerAdvice.processValidationException
    // catches Entity validation exception on CommonControllerAdvice.processDBValidationException
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addSurveyResponse(
        @RequestBody @Valid surveyResponseCreateRequest: SurveyResponseDto.CreateRequest,
        @CurrentUser user: User
    ): SurveyResponseDto.Response {
        return SurveyResponseDto.Response(surveyResponseService.createSurveyResponse(surveyResponseCreateRequest, user))
    }
}
