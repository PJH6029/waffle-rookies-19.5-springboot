package com.wafflestudio.seminar.domain.seminar.excpetion

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class SeminarNotFoundException(detail: String = "Seminar Not Found") :
    DataNotFoundException(ErrorType.SEMINAR_NOT_FOUND, detail)
