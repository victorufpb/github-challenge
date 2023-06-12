package com.challenge.github.application.web.controller

import com.challenge.github.domain.service.GithubService
import org.openapitools.api.UsersApi
import org.openapitools.model.UserRepositoryInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ChallengeController(
    private val githubService: GithubService
): UsersApi {

    override fun getRepositoryInfo(username: String): ResponseEntity<List<UserRepositoryInfoDto>> {
        val userInformation = githubService.getGithubInformationFromUsername(username)
            .map { it.fromDomain() }
        return ResponseEntity.ok().body(userInformation)
    }
}