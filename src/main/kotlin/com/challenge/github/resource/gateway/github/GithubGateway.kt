package com.challenge.github.resource.gateway.github

import com.challenge.github.resource.gateway.github.dto.BranchDto
import com.challenge.github.resource.gateway.github.dto.RepositoryDto

interface GithubGateway {

    fun getRepositories(username: String): List<RepositoryDto>?
    fun getBranchesFromRepository(owner: String, repository: String): List<BranchDto>?
}