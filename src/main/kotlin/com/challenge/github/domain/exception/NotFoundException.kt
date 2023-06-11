package com.challenge.github.domain.exception

import org.springframework.http.HttpStatus

class NotFoundException(
        httpStatus: Int = HttpStatus.NOT_FOUND.value(),
        message: String = "Not found"
) : DefaultException(httpStatus, message) {
}