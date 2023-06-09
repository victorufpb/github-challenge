package com.challenge.github.application.configuration

import lombok.Data
import lombok.RequiredArgsConstructor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "resource.github")
@ConstructorBinding
@Data
@RequiredArgsConstructor
data class GithubProperties(
        val url: String,
        val secret: String
)