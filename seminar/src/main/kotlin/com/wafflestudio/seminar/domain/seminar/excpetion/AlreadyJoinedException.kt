package com.wafflestudio.seminar.domain.seminar.excpetion

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class AlreadyJoinedException(detail: String = "You've joined this seminar"):
        InvalidRequestException(ErrorType.INVALID_REQUEST, detail)