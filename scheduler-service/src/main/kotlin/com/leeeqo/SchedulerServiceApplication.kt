package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@SpringBootApplication
class SchedulerServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SchedulerServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}