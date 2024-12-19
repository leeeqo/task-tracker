package com.leeeqo.controller

import com.leeeqo.dto.AuthenticationRequest
import com.leeeqo.dto.AuthenticationResponse
import com.leeeqo.entity.Client
import com.leeeqo.service.ClientService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class SecurityController(
    private val clientService: ClientService,
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid request: AuthenticationRequest): ResponseEntity<Long> =
        ResponseEntity.ok(clientService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody @Valid request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(clientService.authenticate(request))

    @GetMapping("/validate")
    fun validate(@AuthenticationPrincipal client: Client): ResponseEntity<Long> =
        ResponseEntity.ok(client.id)
}
