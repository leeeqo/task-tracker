package com.leeeqo.controller

import com.leeeqo.dto.*
import com.leeeqo.entity.User
import com.leeeqo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid request: AuthenticationRequest): ResponseEntity<Long> =
        ResponseEntity.ok(userService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody @Valid request: AuthenticationRequest): ResponseEntity<TokenResponse> =
        ResponseEntity.ok(userService.authenticate(request))

    @GetMapping("/validate")
    fun validate(@AuthenticationPrincipal user: User): ResponseEntity<Long> =
        ResponseEntity.ok(user.id)
}
