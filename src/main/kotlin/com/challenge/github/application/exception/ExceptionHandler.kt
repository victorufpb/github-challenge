package com.challenge.github.application.exception

import com.challenge.github.domain.exception.DefaultException
import org.openapitools.model.ErrorResponseDto
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
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

    @ExceptionHandler(value = [HttpMediaTypeNotAcceptableException::class])
    fun handleHttpMediaException(ex: HttpMediaTypeNotAcceptableException): ResponseEntity<ErrorResponseDto> {
        println("Handling with ${ex.javaClass}")
        return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .header(CONTENT_TYPE, APPLICATION_JSON.toString())
            .body(
                ErrorResponseDto(
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    message = "acceptable MIME type $APPLICATION_JSON"
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