package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class InvalidSignInException(detail: String = "Email or password is wrong"):
        InvalidRequestException(ErrorType.INVALID_REQUEST, detail)