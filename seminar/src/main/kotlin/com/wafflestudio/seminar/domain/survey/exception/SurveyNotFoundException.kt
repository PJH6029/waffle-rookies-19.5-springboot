package com.wafflestudio.seminar.domain.survey.exception

import com.wafflestudio.seminar.common.exception.WaffleException
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException

class SurveyNotFoundException : WaffleNotFoundException("SURVEY NOT FOUND")
