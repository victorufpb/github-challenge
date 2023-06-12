package com.challenge.github.domain.exception

abstract class DefaultException(
        val httpStatus: Int,
        override val message: String
): Exception(message)