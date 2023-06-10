package com.challenge.github.resource.interceptor

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RequiredArgsConstructor
@Component
class ConfigInterceptor(val interceptor: Interceptor): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(interceptor)
    }
}