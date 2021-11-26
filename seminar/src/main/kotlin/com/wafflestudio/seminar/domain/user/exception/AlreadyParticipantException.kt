package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class AlreadyParticipantException(detail: String = "You are already participant") :
    InvalidRequestException(ErrorType.ALREADY_PARTICIPANT, detail)
