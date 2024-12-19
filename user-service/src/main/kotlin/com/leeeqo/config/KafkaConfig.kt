package com.leeeqo.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {

    @Bean
    fun admin(): KafkaAdmin {
        val configs: Map<String, Any> = mutableMapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers
        )

        return KafkaAdmin(configs)
    }

    @Bean
    fun createTopic(): NewTopic =
        NewTopic("USER_UPDATE", 1, 2)
}