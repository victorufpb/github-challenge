package com.challenge.github.domain.exception

import org.springframework.http.HttpStatus

class HeaderNotAllowedException(
        httpStatus: Int = HttpStatus.NOT_ACCEPTABLE.value(),
        message: String = "Not acceptable"
) : DefaultException(httpStatus, message) {
}