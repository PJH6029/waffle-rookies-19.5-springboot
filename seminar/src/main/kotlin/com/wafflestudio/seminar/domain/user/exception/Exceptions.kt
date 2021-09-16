package com.wafflestudio.seminar.domain.user.exception

import com.wafflestudio.seminar.common.exception.WaffleNotFoundException

class UserNotFoundException: WaffleNotFoundException("USER NOT FOUND")
