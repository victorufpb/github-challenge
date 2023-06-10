package com.challenge.github.domain.exception

import org.springframework.http.HttpStatus

abstract class DefaultException(
        val httpStatus: Int,
        override val message: String
): Exception(message)