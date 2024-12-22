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
class SchedulerServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SchedulerServiceApplication::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}
