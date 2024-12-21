package com.leeeqo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration


@EnableFeignClients
@SpringBootApplication
@ImportAutoConfiguration(classes = [FeignAutoConfiguration::class])
class TaskServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(TaskServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}