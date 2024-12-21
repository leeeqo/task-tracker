package com.leeeqo.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class AssigneesRequest(
    @field:Valid
    @field:NotEmpty
    val assignees: List<Long>
)
