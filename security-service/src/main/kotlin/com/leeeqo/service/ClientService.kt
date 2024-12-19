package com.leeeqo.service

import com.leeeqo.dto.AuthenticationRequest
import com.leeeqo.dto.AuthenticationResponse
import com.leeeqo.entity.Client
import com.leeeqo.exception.EmailAlreadyExistsException
import com.leeeqo.exception.UserBadCredentialsException
import com.leeeqo.exception.UserNotFoundException
import com.leeeqo.repository.ClientRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ClientService (
    private val clientRepository: ClientRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val clientDetailsService: UserDetailsService,
    private val tokenService: TokenService
) {

    fun register(request: AuthenticationRequest): Long {
        clientRepository.findByEmail(request.email)?.let {
            throw EmailAlreadyExistsException("User ${request.email} already exists.")
        }

        val client = Client(
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        val savedUser = clientRepository.saveAndFlush(client)

        return savedUser.id
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.email,
                    request.password
                )
            )
        } catch (ex: InternalAuthenticationServiceException) {
            throw UserNotFoundException("User ${request.email} not found.")
        } catch (ex: BadCredentialsException) {
            throw UserBadCredentialsException("Wrong password for client ${request.email}")
        }

        val client = clientDetailsService.loadUserByUsername(request.email)

        tokenService.deleteToken(client)
        val jwt = jwtService.generateJwt(client)
        tokenService.createToken(client, jwt)

        return AuthenticationResponse(jwt)
    }
}
