package com.leeeqo.config

import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class ApiGatewayConfig {

    @Bean
    fun httpMessageConverters() = HttpMessageConverters(GsonHttpMessageConverter())

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val configuration = CorsConfiguration()
        configuration.applyPermitDefaultValues()
        configuration.allowedOrigins =
            listOf("http://localhost:8080", "http://localhost:8000", "http://localhost:3000")
        configuration.addAllowedMethod(HttpMethod.DELETE)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return CorsWebFilter(source)
    }

    @Bean
    fun restTemplate() = RestTemplate()
}
