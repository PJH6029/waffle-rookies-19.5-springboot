package com.wafflestudio.seminar.domain.seminar.excpetion

import com.wafflestudio.seminar.global.common.exception.DataNotFoundException
import com.wafflestudio.seminar.global.common.exception.ErrorType

class SeminarParticipantNotFound(detail: String = "SeminarParticipant not found") :
    DataNotFoundException(ErrorType.SEMINAR_PARTICIPANT_NOT_FOUND, detail)
