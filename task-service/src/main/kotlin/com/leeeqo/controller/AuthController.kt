package com.leeeqo.controller

import com.leeeqo.dto.*
import com.leeeqo.entity.User
import com.leeeqo.service.AuthenticationService
import com.leeeqo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging

private val kLogger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    //private val authenticationService: AuthenticationService,
) {

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid request: AuthenticationRequest
    ): ResponseEntity<Boolean> = //ResponseEntity<AuthenticationResponse> =
        //ResponseEntity.ok(authenticationService.register(request))
        ResponseEntity.ok(userService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody @Valid request: AuthenticationRequest
    ): ResponseEntity<TokenResponse> = //AuthenticationResponse> =
        //ResponseEntity.ok(authenticationService.authentication(authRequest))
        ResponseEntity.ok(userService.authenticate(request))

    @GetMapping("/validate")
    fun validate(@AuthenticationPrincipal user: User): ResponseEntity<Long> =
        ResponseEntity.ok(user.id)

    /*@PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse = TokenResponse(token = authenticationService.refreshAccessToken(request.token))*/
}