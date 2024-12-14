package com.leeeqo.filter

import com.leeeqo.service.OtherTokenService
import com.leeeqo.service.TokenService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.ErrorResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.time.LocalDateTime

private val kLogger = KotlinLogging.logger {}


@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
    private val otherTokenService: OtherTokenService,
    //private val messageSource: MessageSour
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        kLogger.info { "IN FILTER" }

        val jwt = tokenService.extractJwt(request)

        kLogger.info { "JWT = $jwt" }

        if (!jwt.isNullOrEmpty() && SecurityContextHolder.getContext().authentication == null) {
            try {
                val userDetails: UserDetails = otherTokenService.userDetailsFromJwt(jwt)

                kLogger.info { "UsetDetails SUCCESS" }
                if (otherTokenService.isTokenValid(jwt)) {
                    val authenticationToken: UsernamePasswordAuthenticationToken =
                        createAuthenticationToken(userDetails, request)

                    val context = SecurityContextHolder.createEmptyContext()

                    context.authentication = authenticationToken
                    SecurityContextHolder.setContext(context)
                }
            } catch (e: JwtException) {
                //handleInvalidJwtException(response, e.message)
                // TODO

                kLogger.info { "EXCEPTION THROWN" }

                return
            }
        }

        filterChain.doFilter(request, response);
    }

    private fun createAuthenticationToken(
        userDetails: UserDetails,
        request: HttpServletRequest
    ) = UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails.password,
            userDetails.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }

    @Throws(IOException::class)
    private fun handleInvalidJwtException(response: HttpServletResponse, message: String) {
        /*val errorResponse: ErrorResponse = ErrorResponse.builder()
            .description(messageSource.getProperty("jwt.invalid"))
            .code(HttpStatus.FORBIDDEN.value())
            .message(message)
            .timestamp(LocalDateTime.now())
            .build()
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(errorResponse))*/
        // TODO
    }
}