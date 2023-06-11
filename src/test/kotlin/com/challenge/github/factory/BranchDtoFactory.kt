package com.challenge.github.factory

import com.challenge.github.resource.gateway.github.dto.BranchDto
import com.challenge.github.resource.gateway.github.dto.Commit

object BranchDtoFactory {

    fun buildBranchDto(fork: Boolean = false) = BranchDto(
            name = "testBranch",
            fork = fork,
            commit = Commit(
                    sha = "d6cd1e2bd19e03a81132a23b2025920577f84e37"
            )
    )
}