package com.challenge.github.application.web.controller

import com.challenge.github.factory.UsernameRepositoryInformationFactory.buildUsernameRepositoryInformation
import com.challenge.github.domain.service.GithubService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChallengeControllerTest{

    private val githubService = mockk<GithubService>()
    private val challengeController = ChallengeController(githubService)
    @Test
    fun `given a username, when try to get repository and branch information, should get successfully`() {
        val userName = "unitTest"
        val repoInfo = listOf(buildUsernameRepositoryInformation(userName))

        every { githubService.getGithubInformationFromUsername(userName) } returns repoInfo

        val response = challengeController.getRepositoryInfo(userName).body!!.first()

        verify { githubService.getGithubInformationFromUsername(userName) }

        repoInfo[0].let { info ->
            assertEquals(info.repositoryName, response.repositoryName)
            assertEquals(info.ownerLogin, response.ownerLogin)
            assertEquals(
                    info.branchesInformation.first().commitSha, response.branchesInfo?.first()?.commitSha
            )

        }

    }


}