package com.challenge.github.application.web.controller

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import util.IntegrationTestConfiguration

class ChallengeControllerIntegrationTest : IntegrationTestConfiguration() {

    @Test
    fun `given a valid username, when try to get information, should get successfully `() {
        val username = "githubSuccess"
        Given {
            headers(basicAuthHeader)
        } When {
            get("/users/$username/repos")
        } Then {
            log().all()
            statusCode(HttpStatus.OK.value())
        }
    }

    @Test
    fun `given an invalid username, when try to get information, should get not found`() {
        val username = "userNotFound"
        Given {
            headers(basicAuthHeader)
        } When {
            get("/users/$username/repos")
        } Then {
            log().all()
            statusCode(HttpStatus.NOT_FOUND.value())
        }
    }

    @Test
    fun `given an invalid accept header, when try to get information, should get not acceptable`() {
        val username = "userNotFound"
        Given {
            headers(
                basicAuthHeader.apply {
                    set("Accept","application/xml")
                }
            )

        } When {
            get("/users/$username/repos")
        } Then {
            log().all()
            statusCode(HttpStatus.NOT_ACCEPTABLE.value())
        }
    }

    @Test
    fun `given an invalid accept header, when try to get information, should get pre condition failed`() {
        val username = "emptyUser"
        Given {
            headers(basicAuthHeader)

        } When {
            get("/users/$username/repos")
        } Then {
            log().all()
            statusCode(HttpStatus.PRECONDITION_FAILED.value())
        }
    }
}