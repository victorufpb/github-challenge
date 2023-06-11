package com.challenge.github.application.exception

import com.challenge.github.domain.exception.DefaultException
import com.challenge.github.domain.exception.EmptyRepositoryException
import com.challenge.github.domain.exception.HeaderNotAllowedException
import com.challenge.github.domain.exception.NotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.PRECONDITION_FAILED
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.HttpMediaTypeNotAcceptableException
import java.lang.Exception
import java.util.stream.Stream

class ExceptionHandlerTest{

    private val handler = ExceptionHandler()
    @ParameterizedTest
    @MethodSource("getExceptionList")
    fun `given a default exception, when handle it, should return a ErrorResponseDto`(defaultException: DefaultException, httpStatus: Int) {
        val response = handler.handleDefaultException(defaultException)

        response.body?.apply {
            assertEquals(status, httpStatus)
        }
    }

    @Test
    fun `given a HttpMediaTypeNotAcceptableException, when handle it, should return a NOT_ACCEPTABLE status`() {
        val ex = HttpMediaTypeNotAcceptableException("Error with MediaType")
        val response = handler.handleHttpMediaException(ex)

        assertEquals(response.body?.status, NOT_ACCEPTABLE.value())
    }

    @Test
    fun `given a non mapped exception, when handle it, should return a INTERNAL_SERVER_ERROR status`() {
        val ex = Exception("Non mapped exception")
        val response = handler.handleException(ex)

        assertEquals(response.body?.status, INTERNAL_SERVER_ERROR.value())
    }

    companion object {
        @JvmStatic
        fun getExceptionList(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(EmptyRepositoryException(), PRECONDITION_FAILED.value()),
                    Arguments.of(HeaderNotAllowedException(), NOT_ACCEPTABLE.value()),
                    Arguments.of(NotFoundException(), NOT_FOUND.value())
            )
        }
    }
}