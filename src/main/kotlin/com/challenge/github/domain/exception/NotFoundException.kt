package com.challenge.github.domain.exception

import org.springframework.http.HttpStatus

class NotFoundException(
        type: Int = HttpStatus.NOT_FOUND.value(),
        message: String = "Not found"
) : DefaultException(type, message) {
}