package com.challenge.github.factory

import com.challenge.github.resource.gateway.github.dto.Owner
import com.challenge.github.resource.gateway.github.dto.RepositoryDto

object RepositoryDtoFactory {

    fun buildRepositoryDto(ownerName : String = "unitTest") = RepositoryDto(
            name = "repository-name",
            owner = Owner(login = ownerName)
    )
}