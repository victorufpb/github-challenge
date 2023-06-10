package com.challenge.github.resource.interceptor

import com.challenge.github.application.configuration.GithubProperties
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class Interceptor(val githubProperties: GithubProperties): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        request.setAttribute(AUTHORIZATION, githubProperties.secret)
        return true
    }
}