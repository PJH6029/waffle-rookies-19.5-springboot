package com.wafflestudio.seminar.domain.survey.exception

import com.wafflestudio.seminar.common.exception.WaffleNotFoundException
import com.wafflestudio.seminar.global.common.exception.DataNotFoundException

class SurveyNotFoundException : DataNotFoundException("SURVEY NOT FOUND")
