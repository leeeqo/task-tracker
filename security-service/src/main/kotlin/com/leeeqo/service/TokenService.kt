package com.leeeqo.service

import com.leeeqo.entity.Client
import com.leeeqo.entity.Token
import com.leeeqo.repository.TokenRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtService: JwtService,
    private val tokenRepository: TokenRepository,
    private val userDetailsService: UserDetailsService,
) {

    fun createToken(client: Client, jwt: String) {
        tokenRepository.save(
            Token(
                client = client,
                jwt = jwt,
                expired = false,
                revoked = false
            )
        )
    }

    fun deleteToken(client: Client) {
        tokenRepository.findByClient(client)?.let {
            tokenRepository.delete(it)
        }
    }

    fun isTokenValid(jwt: String): Boolean {
        val user = userDetailsFromJwt(jwt)
        val token = tokenRepository.findByJwt(jwt) ?: return false

        return !token.expired && !token.revoked && jwtService.isJwtValid(jwt, user)
    }

    fun userDetailsFromJwt(jwt: String): UserDetails {
        val email = jwtService.extractEmail(jwt)

        return userDetailsService.loadUserByUsername(email)
    }
}
