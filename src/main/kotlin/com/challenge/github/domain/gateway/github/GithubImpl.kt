package com.challenge.github.domain.gateway.github

import com.challenge.github.application.configuration.GithubProperties
import com.challenge.github.resource.gateway.github.GithubGateway
import com.challenge.github.resource.gateway.github.dto.BranchDto
import com.challenge.github.resource.gateway.github.dto.RepositoryDto
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class GithubImpl(
        private val githubProperties: GithubProperties,
        private val restTemplate: RestTemplate
): GithubGateway {

    override fun getRepositories(username: String): List<RepositoryDto>? {
        return restTemplate.exchange<List<RepositoryDto>>(
                githubProperties.url+ REPOSITORY_PATH,
                HttpMethod.GET,
                HttpEntity(
                        null,
                        HttpHeaders().apply {
                            set(ACCEPT, GITHUB_HEADER)
                        }
                ),
                mapOf("username" to username)
        ).body
    }

    override fun getBranchesFromRepository(owner: String, repository: String): List<BranchDto>? {
        return restTemplate.exchange<List<BranchDto>>(
                githubProperties.url+ BRANCH_PATH,
                HttpMethod.GET,
                HttpEntity(
                        null,
                        HttpHeaders().apply {
                            set(ACCEPT, GITHUB_HEADER)
                        }
                ),
                mapOf(
                        "owner" to owner,
                        "repo" to repository
                )
        ).body
    }

    companion object {
        private const val REPOSITORY_PATH = "/users/{username}/repos"
        private const val BRANCH_PATH = "/repos/{owner}/{repo}/branches"
        private const val GITHUB_HEADER = "application/vnd.github+json"
    }
}