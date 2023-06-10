package com.challenge.github.application.configuration

import lombok.Data
import lombok.RequiredArgsConstructor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UserProperties::class)
class UserConfiguration

@ConfigurationProperties(prefix = "external.user")
@ConstructorBinding
@Data
@RequiredArgsConstructor
data class UserProperties(
        val name: String,
        val secret: String
)