package com.challenge.github.domain.entity

import org.openapitools.model.UserRepositoryInfoDto

data class UsernameRepositoryInformation(
    val repositoryName: String,
    val ownerLogin: String,
    val branchesInformation: List<Branch>
){
    fun fromDomain() = UserRepositoryInfoDto(
        repositoryName = repositoryName,
        ownerLogin = ownerLogin,
        branchesInfo = branchesInformation.map { it.fromDomain() }
    )
}
