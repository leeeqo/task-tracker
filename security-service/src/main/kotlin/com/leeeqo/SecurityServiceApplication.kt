package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@SpringBootApplication
class SecurityServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SecurityServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}