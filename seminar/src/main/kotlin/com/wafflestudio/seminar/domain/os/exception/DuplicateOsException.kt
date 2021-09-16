package com.wafflestudio.seminar.domain.os.exception

import com.wafflestudio.seminar.global.common.exception.DuplicateDataException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class DuplicateOsException(detail: String = "Duplicate OS"):
    DuplicateDataException(ErrorType.DUPLICATE_OS, detail)