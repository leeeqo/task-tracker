package com.leeeqo.controller

import com.leeeqo.dto.*
import com.leeeqo.service.AuthenticationService
import com.leeeqo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    //private val userService: UserService,
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.register(request))

    @PostMapping
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.authentication(authRequest))

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse = TokenResponse(token = authenticationService.refreshAccessToken(request.token))
}