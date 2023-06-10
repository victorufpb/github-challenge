package com.challenge.github

import com.challenge.github.application.configuration.GithubProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(GithubProperties::class)
class GithubChallengeApplication

fun main(args: Array<String>) {
	runApplication<GithubChallengeApplication>(*args)
}
