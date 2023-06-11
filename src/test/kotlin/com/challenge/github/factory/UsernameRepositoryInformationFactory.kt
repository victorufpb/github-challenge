package com.challenge.github.factory

import com.challenge.github.domain.entity.Branch
import com.challenge.github.domain.entity.UsernameRepositoryInformation

object UsernameRepositoryInformationFactory {

    fun buildUsernameRepositoryInformation(repositoryName: String = "github-test") = UsernameRepositoryInformation(
            repositoryName = repositoryName,
            ownerLogin = "unittest",
            branchesInformation = listOf(
                    Branch(
                            name = "myTestBranch",
                            commitSha = "f757a5f53b8dc7a7bf0cb33ceab73cfd2272183b"
                    )
            )
    )
}