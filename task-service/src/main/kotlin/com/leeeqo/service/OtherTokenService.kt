package com.leeeqo.service

import com.leeeqo.entity.Token
import com.leeeqo.entity.User
import com.leeeqo.exception.EmailNotFoundException
import com.leeeqo.exception.InvalidTokenException
import com.leeeqo.exception.UserNotFoundException
import com.leeeqo.repository.TokenRepository
import com.leeeqo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class OtherTokenService(
    private val tokenService: TokenService,
    private val tokenRepository: TokenRepository,
    private val userDetailsService: JwtUserDetailsService,
) {

    fun createToken(user: User, jwt: String) {
        tokenRepository.save(
            Token(
                user = user,
                jwt = jwt,
                expired = false,
                revoked = false
            )
        )
    }

    fun deleteToken(user: User) {
        tokenRepository.findByUser(user)?.let {
            tokenRepository.delete(it)
        }
    }

    fun isTokenValid(jwt: String): Boolean {
        val user = userDetailsFromJwt(jwt)
        val token = tokenRepository.findByJwt(jwt) ?: return false

        if (token.expired || token.revoked || !tokenService.isJwtValid(jwt, user)) {
            throw InvalidTokenException("Invalid token.")
        }

        return true
    }

    fun userDetailsFromJwt(jwt: String): UserDetails {
        val email = tokenService.extractEmail(jwt)

        return userDetailsService.loadUserByUsername(email)
    }
}