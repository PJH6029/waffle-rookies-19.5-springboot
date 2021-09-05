package com.wafflestudio.seminar.domain.os.exception

import com.wafflestudio.seminar.common.exception.WaffleException
import com.wafflestudio.seminar.common.exception.WaffleNotFoundException

class OsNotFoundException : WaffleNotFoundException("OS NOT FOUND")
