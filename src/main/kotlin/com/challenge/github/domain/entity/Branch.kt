package com.challenge.github.domain.entity

import org.openapitools.model.BranchInfoDto

data class Branch(
        val name: String,
        val commitSha: String
) {
    fun fromDomain() = BranchInfoDto(
            name = name,
            commitSha = commitSha
    )
}
