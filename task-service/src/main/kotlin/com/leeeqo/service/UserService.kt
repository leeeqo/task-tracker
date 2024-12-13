package com.leeeqo.service

import com.leeeqo.dto.AuthenticationRequest
import com.leeeqo.dto.UserDTO
import com.leeeqo.entity.User
import com.leeeqo.exception.EmailAlreadyExistsException
import com.leeeqo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val authenticationService: AuthenticationService
) {




}