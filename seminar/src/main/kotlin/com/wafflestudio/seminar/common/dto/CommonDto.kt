package com.wafflestudio.seminar.common.dto

class CommonDto {
    data class ErrorResponse(
        var message: String? = "",
        var status: Int? = null,
    )
}