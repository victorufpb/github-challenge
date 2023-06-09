package com.challenge.github.domain.exception

import org.springframework.http.HttpStatus

class EmptyRepositoryException(
        httpStatus: Int = HttpStatus.PRECONDITION_FAILED.value(),
        message: String = "There was a pre condition that failed"
) : DefaultException(httpStatus, message) {
}