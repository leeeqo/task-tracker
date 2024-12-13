package com.leeeqo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class AuthenticationRequest (
    @field:NotEmpty
    @field:Email
    val email: String,

    @field:NotEmpty
    @field:Size(min = 8, max = 16)
    val password: String
)