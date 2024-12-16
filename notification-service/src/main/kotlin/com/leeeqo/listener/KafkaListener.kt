package com.leeeqo.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.leeeqo.service.EmailService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val kLogger = KotlinLogging.logger {}

@Service
class KafkaListener(
    //@Value("\${spring.kafka.topics.sending-tasks}")
    //private val topic: String,

    private val emailService: EmailService
) {

    companion object {
        const val TOPIC = "EMAIL_SENDING_TASKS"

        val objectMapper = ObjectMapper()
    }

    @KafkaListener(topics = [TOPIC], groupId = "task-tracker")
    fun listen(message: String) {
        // TODO - catch JsonProcessingException
        kLogger.info { "NOTIFICATION: message was caught" }

        val msg = objectMapper.readValue(message, Message::class.java)

        kLogger.info { "NOTIFICATION: sending it by Email" }

        emailService.send(msg.recipient, msg.subject, msg.text)
    }

    private data class Message(
        val recipient: String = "",
        val subject: String = "",
        val text: String = "",
    )
}
