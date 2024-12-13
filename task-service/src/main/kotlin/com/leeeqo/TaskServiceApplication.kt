package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@SpringBootApplication
class TaskServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(TaskServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}