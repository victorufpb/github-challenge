package com.challenge.github.resource.gateway.github.dto

import com.challenge.github.domain.entity.Repository
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RepositoryDto(
        val name: String,
        val owner: Owner
){
    fun toDomain() = Repository(
            name = name,
            ownerLogin = owner.login
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Owner(
        val login: String
)
