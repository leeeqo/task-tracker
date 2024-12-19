package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class UserServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(UserServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}