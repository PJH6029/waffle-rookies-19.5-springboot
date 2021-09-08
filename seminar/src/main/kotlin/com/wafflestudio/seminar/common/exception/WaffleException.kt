package com.wafflestudio.seminar.common.exception

import java.lang.RuntimeException

open class WaffleException(private val detail: String) : RuntimeException(detail)

open class WaffleNotFoundException(private val detail: String): WaffleException(detail)

open class WaffleDuplicateException(private val detail: String): WaffleException(detail)