package com.leeeqo.service

import com.leeeqo.dto.AuthenticationRequest
import com.leeeqo.dto.AuthenticationResponse
import com.leeeqo.dto.TokenResponse
import com.leeeqo.dto.UserDTO
import com.leeeqo.entity.User
import com.leeeqo.exception.EmailAlreadyExistsException
import com.leeeqo.exception.UserBadCredentialsException
import com.leeeqo.exception.UserNotFoundException
import com.leeeqo.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val authenticationService: AuthenticationService,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: JwtUserDetailsService,
    private val otherTokenService: OtherTokenService
) {

    fun register(request: AuthenticationRequest): Boolean {
        userRepository.findByEmail(request.email)?.let {
            throw EmailAlreadyExistsException("User ${request.email} already exists.")
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.saveAndFlush(user)

        return true
    }

    fun authenticate(request: AuthenticationRequest): TokenResponse {
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
            throw UserBadCredentialsException("Wrong password for user ${request.email}")
        }

        val user = userDetailsService.loadUserByUsername(request.email)

        otherTokenService.deleteToken(user)
        val jwt = tokenService.generateJwt(user)
        otherTokenService.createToken(user, jwt)

        return TokenResponse(jwt)

        /*val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )*/
    }
}