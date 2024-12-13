package com.leeeqo.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Service
class TokenService (
    @Value("\${token.signing.value}")
    private val secret: String
) {

    private val signingKey: SecretKey
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            //return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
            return Keys.hmacShaKeyFor(keyBytes)
        }

    fun generateToken(subject: String, expiration: Date, additionalClaims: Map<String, Any> = emptyMap()): String {
        return Jwts.builder()
            .setClaims(additionalClaims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expiration)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractEmail(token: String): String =
        extractAllClaims(token).subject

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJwt(token)
            .body
    }
}