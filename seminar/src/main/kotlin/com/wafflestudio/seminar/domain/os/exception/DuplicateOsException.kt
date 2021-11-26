package com.wafflestudio.seminar.domain.os.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class DuplicateOsException(detail: String = "Duplicate OS") :
    InvalidRequestException(ErrorType.INVALID_REQUEST, detail)
