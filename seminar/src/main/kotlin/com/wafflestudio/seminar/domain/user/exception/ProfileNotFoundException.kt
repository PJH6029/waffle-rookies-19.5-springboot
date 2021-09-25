package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class ProfileNotFoundException(detail: String = "") :
    DataNotFoundException(ErrorType.PROFILE_NOT_FOUND, detail)