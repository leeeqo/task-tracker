package com.leeeqo.filter

import com.leeeqo.exception.InvalidTokenException
import com.leeeqo.service.TokenService
import com.leeeqo.service.JwtService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val kLogger = KotlinLogging.logger {}


@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val tokenService: TokenService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwt = jwtService.extractJwt(request)

        if (!jwt.isNullOrEmpty() && SecurityContextHolder.getContext().authentication == null) {
            try {
                val userDetails: UserDetails = tokenService.userDetailsFromJwt(jwt)

                if (tokenService.isTokenValid(jwt)) {
                    val authenticationToken: UsernamePasswordAuthenticationToken =
                        createAuthenticationToken(userDetails, request)

                    val context = SecurityContextHolder.createEmptyContext()

                    context.authentication = authenticationToken
                    SecurityContextHolder.setContext(context)
                } else {
                    throw InvalidTokenException("Invalid token.")
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

    // TODO
    /*@Throws(IOException::class)
    private fun handleInvalidJwtException(response: HttpServletResponse, message: String) {
        val errorResponse: ErrorResponse = ErrorResponse.builder()
            .description(messageSource.getProperty("jwt.invalid"))
            .code(HttpStatus.FORBIDDEN.value())
            .message(message)
            .timestamp(LocalDateTime.now())
            .build()
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }*/
}
