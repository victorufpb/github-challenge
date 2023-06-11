package com.challenge.github.domain.service

import com.challenge.github.domain.exception.EmptyRepositoryException
import com.challenge.github.domain.exception.NotFoundException
import com.challenge.github.factory.BranchDtoFactory.buildBranchDto
import com.challenge.github.factory.RepositoryDtoFactory.buildRepositoryDto
import com.challenge.github.resource.gateway.github.GithubGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runners.model.MultipleFailureException.assertEmpty
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import java.lang.Exception

class GithubServiceTest {
    private val githubGateway = mockk<GithubGateway>()
    private val githubService = GithubService(githubGateway)

    @Test
    fun `given a username, when try to get information, should get successfully`() {
        val username = "githubUser"
        val repositories = listOf(buildRepositoryDto(username))
        val branches = listOf(buildBranchDto())
        every { githubGateway.getRepositories(username) } returns repositories
        every {
            githubGateway.getBranchesFromRepository(repositories[0].owner.login, repositories[0].name)
        } returns branches

        val response = githubService.getGithubInformationFromUsername(username)

        verify { githubGateway.getRepositories(username) }
        verify {
            githubGateway.getBranchesFromRepository(repositories[0].owner.login, repositories[0].name)
        }

        assertEquals(repositories[0].name, response[0].repositoryName)
        assertEquals(repositories[0].owner.login, response[0].ownerLogin)
        assertEquals(branches[0].name, response[0].branchesInformation[0].name)
    }

    @Test
    fun `given a username, when try to get information and there is only fork branch, should not retrieve them`() {
        val username = "githubUser"
        val repositories = listOf(buildRepositoryDto(username))
        val branches = listOf(buildBranchDto(fork = true))
        val expectedBranches = 0

        every { githubGateway.getRepositories(username) } returns repositories
        every {
            githubGateway.getBranchesFromRepository(repositories[0].owner.login, repositories[0].name)
        } returns branches

        val response = githubService.getGithubInformationFromUsername(username)

        verify { githubGateway.getRepositories(username) }
        verify {
            githubGateway.getBranchesFromRepository(repositories[0].owner.login, repositories[0].name)
        }

        assertEquals(repositories[0].name, response[0].repositoryName)
        assertEquals(repositories[0].owner.login, response[0].ownerLogin)
        assertEquals(expectedBranches, response[0].branchesInformation.size)
    }

    @Test
    fun `given an invalid username, when try to get information, should throws NotFoundException`() {
        val username = "githubUser"

        every { githubGateway.getRepositories(username) } throws HttpClientErrorException(HttpStatus.NOT_FOUND)

        assertThrows<NotFoundException> {
            githubService.getGithubInformationFromUsername(username)
        }

        verify { githubGateway.getRepositories(username) }
        verify(exactly = 0) {
            githubGateway.getBranchesFromRepository(any(), any())
        }
    }

    @Test
    fun `given a username, when try to get information and throws Exception, should rethrows it`() {
        val username = "githubUser"

        every { githubGateway.getRepositories(username) } throws Exception("Unmapped exception")

        assertThrows<Exception> {
            githubService.getGithubInformationFromUsername(username)
        }

        verify { githubGateway.getRepositories(username) }
        verify(exactly = 0) {
            githubGateway.getBranchesFromRepository(any(), any())
        }
    }

    @Test
    fun `given a username, when try to get information and there is no repository available, should throws EmptyRepositoryException`() {
        val username = "githubUser"

        every { githubGateway.getRepositories(username) } returns listOf()

        assertThrows<EmptyRepositoryException> {
            githubService.getGithubInformationFromUsername(username)
        }

        verify { githubGateway.getRepositories(username) }
        verify(exactly = 0) {
            githubGateway.getBranchesFromRepository(any(), any())
        }
    }

}