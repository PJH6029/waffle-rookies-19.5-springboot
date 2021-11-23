package com.wafflestudio.seminar.domain.survey.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class SurveyResponseNotFoundException(detail: String = "Survey response not found") :
    DataNotFoundException(ErrorType.SURVEY_RESPONSE_NOT_FOUND, detail)
