package com.leeeqo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserRequest(
    @Size(max = 100)
    @NotNull
    val name: String = "",

    @NotNull
    @Email
    val email: String = "",

    @Size(max = 20)
    val phone: String = "",
)