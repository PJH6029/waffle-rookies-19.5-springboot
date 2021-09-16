package com.wafflestudio.seminar.domain.os.exception

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class OsNotFoundException(detail: String="OS not found") :
    DataNotFoundException(ErrorType.OS_NOT_FOUND, detail)
