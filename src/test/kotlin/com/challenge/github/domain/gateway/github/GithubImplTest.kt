package com.challenge.github.domain.gateway.github

import com.challenge.github.application.configuration.GithubProperties
import com.challenge.github.factory.BranchDtoFactory.buildBranchDto
import com.challenge.github.factory.RepositoryDtoFactory.buildRepositoryDto
import com.challenge.github.resource.gateway.github.dto.BranchDto
import com.challenge.github.resource.gateway.github.dto.RepositoryDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

class GithubImplTest {

    private val githubProperties = mockk<GithubProperties>(){
        every { url } returns "http://localhost:9092"
        every { secret } returns "123"
    }
    private val restTemplate = mockk<RestTemplate>()
    private val githubGateway = GithubGatewayImpl(githubProperties, restTemplate)

    @Test
    fun `given a username, when try to get repository information, should get successfully`() {
        val username = "githubUnitTest"
        val repositoryResponse = listOf(buildRepositoryDto(username))
        val url = githubProperties.url + REPOSITORY_PATH
        val httpEntity = buildGithubHttpEntity()

        val parameters = mapOf("username" to username)
        every {
            restTemplate.exchange<List<RepositoryDto>>(url, HttpMethod.GET, httpEntity, parameters)
        } returns ResponseEntity.ok().body(repositoryResponse)

        val response = githubGateway.getRepositories(username)

        verify {
            restTemplate.exchange<List<RepositoryDto>>(url, HttpMethod.GET, httpEntity, parameters)
        }

        assertEquals(repositoryResponse[0].name, response?.get(0)?.name)
        assertEquals(repositoryResponse[0].owner.login, response?.get(0)?.owner?.login)
    }

    @Test
    fun `given an owner and repository, when try to get branches information, should get successfully`() {
        val owner = "githubUnitTest"
        val repository = "myRepo"
        val branchesInfo = listOf(buildBranchDto(fork = false))
        val url = githubProperties.url + BRANCH_PATH
        val httpEntity = buildGithubHttpEntity()
        val parameters = mapOf(
                "owner" to owner,
                "repo" to repository
        )

        every {
            restTemplate.exchange<List<BranchDto>>(url, HttpMethod.GET, httpEntity, parameters)
        } returns ResponseEntity.ok().body(branchesInfo)

        val response = githubGateway.getBranchesFromRepository(owner, repository)

        verify {
            restTemplate.exchange<List<BranchDto>>(url, HttpMethod.GET, httpEntity, parameters)
        }

        assertEquals(branchesInfo[0].name, response?.get(0)?.name)
        assertEquals(branchesInfo[0].commit.sha, response?.get(0)?.commit?.sha)
    }

    private fun buildGithubHttpEntity() = HttpEntity(
            null,
            HttpHeaders().apply {
                set(HttpHeaders.ACCEPT, GITHUB_HEADER)
            }
    )
    companion object {
        const val REPOSITORY_PATH = "/users/{username}/repos"
        const val BRANCH_PATH = "/repos/{owner}/{repo}/branches"
        const val GITHUB_HEADER = "application/vnd.github+json"
    }
}