package com.wafflestudio.seminar.domain.survey.api

import com.wafflestudio.seminar.domain.os.service.OperatingSystemService
import com.wafflestudio.seminar.domain.survey.dto.SurveyResponseDto
import com.wafflestudio.seminar.domain.survey.model.SurveyResponse
import com.wafflestudio.seminar.domain.survey.service.SurveyResponseService
import com.wafflestudio.seminar.domain.user.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class SurveyResponseController(
    private val surveyResponseService: SurveyResponseService,
    private val operatingSystemService: OperatingSystemService,
    private val userService: UserService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/")  // TODO trailing slash??
    fun getSurveyResponses(@RequestParam(required = false) os: String?): ResponseEntity<List<SurveyResponseDto.Response>> {
        val surveyResponses =
            if (os != null) surveyResponseService.getSurveyResponsesByOsName(os)
            else surveyResponseService.getAllSurveyResponses()
        val responseBody = surveyResponses.map { modelMapper.map(it, SurveyResponseDto.Response::class.java) }
        return ResponseEntity.ok(responseBody)
    }

    @GetMapping("/{id}/")
    fun getSurveyResponse(@PathVariable("id") id: Long): ResponseEntity<SurveyResponseDto.Response> {
        val surveyResponse = surveyResponseService.getSurveyResponseById(id)
        val responseBody = modelMapper.map(surveyResponse, SurveyResponseDto.Response::class.java)
        return ResponseEntity.ok(responseBody)
    }

    @PostMapping("/")
    fun addSurveyResponse(
        @ModelAttribute @Valid body: SurveyResponseDto.CreateRequest,
        @RequestHeader("User-Id") userId: Long
    ): ResponseEntity<SurveyResponseDto.Response> {
        // TODO 어케 user랑 os를 집어넣지?

        val newSurveyResponse = modelMapper.map(body, SurveyResponse::class.java)

        // 임시
        // TODO 이 로직을 처리하는 장소? (controller or service)
        newSurveyResponse.os = operatingSystemService.getOperatingSystemByName(body.os!!)
        newSurveyResponse.user = userService.getUserById(userId)

        surveyResponseService.addSurveyResponse(newSurveyResponse)

        val responseBody = modelMapper.map(newSurveyResponse, SurveyResponseDto.Response::class.java)
        return ResponseEntity<SurveyResponseDto.Response>(responseBody, HttpStatus.CREATED)
    }
}
