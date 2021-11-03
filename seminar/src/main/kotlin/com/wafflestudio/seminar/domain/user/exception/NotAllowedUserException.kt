package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.NotAllowedException

class NotAllowedUserException(detail: String = ""):
        NotAllowedException(ErrorType.NOT_ALLOWED, detail)