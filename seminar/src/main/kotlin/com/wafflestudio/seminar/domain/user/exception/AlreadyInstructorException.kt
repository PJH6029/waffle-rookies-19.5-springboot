package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.global.common.exception.ConflictException
import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class AlreadyInstructorException(detail: String="You are already instructor") :
        InvalidRequestException(ErrorType.ALREADY_INSTRUCTOR, detail)