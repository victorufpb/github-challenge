package com.challenge.github.application.exception

import com.challenge.github.domain.exception.DefaultException
import com.challenge.github.domain.exception.NotFoundException
import org.openapitools.model.ErrorResponseDto
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [DefaultException::class])
    fun handleDefaultException(defaultException: DefaultException): ResponseEntity<ErrorResponseDto> {
        println("handling with ${defaultException.javaClass}")
        return ResponseEntity.status(defaultException.httpStatus).body(
            ErrorResponseDto(
                    status = defaultException.httpStatus,
                    message = defaultException.message
            )
        )
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(ex: Exception): ResponseEntity<ErrorResponseDto> {
        println("Handling with ${ex.javaClass}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponseDto(
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message = "There was an internal error"
                )
        )
    }
}