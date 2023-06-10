package com.challenge.github.domain.service

import com.challenge.github.domain.entity.Branch
import com.challenge.github.domain.entity.Repository
import com.challenge.github.domain.entity.UsernameRepositoryInformation
import com.challenge.github.domain.exception.EmptyRepositoryException
import com.challenge.github.domain.exception.NotFoundException
import com.challenge.github.resource.gateway.github.GithubGateway
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class GithubService(
        private val githubGateway: GithubGateway
) {

    fun getGithubInformationFromUsername(username: String): List<UsernameRepositoryInformation> {
        val userInfo = mutableListOf<UsernameRepositoryInformation>()
        val repositories = getRepositories(username)

        repositories.forEach { repository ->
            val branches = getBranchesInfoFromRepository(repository.ownerLogin, repository.name)
            userInfo.add(buildUserInformation(repository, branches))
        }

        return userInfo
    }

    private fun getRepositories(username: String): List<Repository> {
        println("Finding repositories from $username")
        val repositories = try {
            githubGateway.getRepositories(username)
        } catch (ex: HttpClientErrorException) {
            if(ex.statusCode == HttpStatus.NOT_FOUND){
                throw NotFoundException(message = "Username $username not found")
            } else{
                throw ex
            }

        }

        if(repositories.isNullOrEmpty()) throw EmptyRepositoryException(message = "Username $username there is no public repository or is empty")
        return repositories.map { it.toDomain() }
    }

    private fun getBranchesInfoFromRepository(owner: String, repository: String): List<Branch> {
        println("Finding branches of $owner from repository $repository")
        return githubGateway.getBranchesFromRepository(owner, repository)?.filter { !it.fork }?.map { it.toDomain() }
                ?: listOf()
    }

    private fun buildUserInformation(repository: Repository, branches: List<Branch>) = UsernameRepositoryInformation(
            repositoryName = repository.name,
            ownerLogin = repository.ownerLogin,
            branchesInformation = branches
    )
}