package com.leeeqo.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService (
    @Value("\${token.signing.value}")
    private val secret: String
) {

    private val signingKey: SecretKey
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return Keys.hmacShaKeyFor(keyBytes)
        }

    fun generateJwt(userDetails: UserDetails): String {
        return Jwts.builder()
            .setClaims(hashMapOf<String, Any>())
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractEmail(token: String): String =
        extractAllClaims(token).subject

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun isJwtValid(jwt: String, userDetails: UserDetails): Boolean =
        extractEmail(jwt) == userDetails.username && !isJwtExpired(jwt)

    fun isJwtExpired(jwt: String) =
        extractAllClaims(jwt).expiration.before(Date())

    fun extractJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        // TODO - prefix
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }

        return null
    }
}
