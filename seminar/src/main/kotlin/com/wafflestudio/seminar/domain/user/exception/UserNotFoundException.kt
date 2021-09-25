package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class UserNotFoundException(detail: String="User not found") :
    DataNotFoundException(ErrorType.USER_NOT_FOUND, detail)