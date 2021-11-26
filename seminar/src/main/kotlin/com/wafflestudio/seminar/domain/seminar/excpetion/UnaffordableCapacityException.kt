package com.wafflestudio.seminar.domain.seminar.excpetion

import com.wafflestudio.seminar.global.common.exception.ErrorType
import com.wafflestudio.seminar.global.common.exception.InvalidRequestException

class UnaffordableCapacityException(detail: String = "Capacity is smaller than the number of participants") :
    InvalidRequestException(ErrorType.INVALID_REQUEST, detail)
