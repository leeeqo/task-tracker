package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class SchedulerServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SchedulerServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}