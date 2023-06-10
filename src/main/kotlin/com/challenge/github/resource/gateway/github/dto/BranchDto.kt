package com.challenge.github.resource.gateway.github.dto

import com.challenge.github.domain.entity.Branch
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.beans.factory.annotation.Value

@JsonIgnoreProperties(ignoreUnknown = true)
data class BranchDto(
        val name: String,
        val fork: Boolean,
        @Value(value = "commit")
        val commit: Commit
){
        fun toDomain() = Branch(
                name = name,
                commitSha = commit.sha
        )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Commit(
        val sha: String
)