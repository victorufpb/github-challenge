package util

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("integration-test")
@AutoConfigureWireMock(port = 6500, stubs = ["classpath:/wiremock-stubs"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTestConfiguration {

    @Value("\${local.server.port}")
    private var serverPort = -1

    @Value("\${external.user.name}")
    private var userName = ""

    @Value("\${external.user.secret}")
    private var userPassword = ""


    @BeforeEach
    fun setupRestAssured() {
        if(serverPort > 0 ){
            RestAssured.port = serverPort
            println("Rest-assured ready on port $serverPort")
        }
    }

    protected val basicAuthHeader
        get() = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set(HttpHeaders.AUTHORIZATION, "Basic ${encodePassword()}")
        }

    private fun encodePassword(): String = Base64.getEncoder().encodeToString("$userName:$userPassword".toByteArray())
}