package com.leeeqo.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.leeeqo.dto.DailySummary
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    @Value("\${spring.kafka.topics.sending-tasks}")
    private val topic: String,

    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        const val SUBJECT = "Daily Tasks Summary"
        const val HELLO_MESSAGE = "Hello, %s! Your task statistics: \n"
        const val FINISHED_TASKS = "Today you have %d finished tasks:\n %s \n"
        const val UNFINISHED_TASKS = "Today you have %d unfinished tasks:\n %s \n"

        val objectMapper = ObjectMapper()
    }

    fun sendSummary(summary: DailySummary) {
        val jsonMessage = objectMapper.writeValueAsString(buildMessage(summary))

        kafkaTemplate.send(topic, jsonMessage) // TODO - catch JsonProcessingException
    }

    private fun buildMessage(summary: DailySummary): Message {
        var text = HELLO_MESSAGE.format(summary.email)

        if (summary.finishedTasks.isNotEmpty()) {
            text += FINISHED_TASKS.format(
                summary.finishedTasks.size,
                summary.finishedTasks.toString()
            )
        }

        if (summary.unfinishedTasks.isNotEmpty()) {
            text += UNFINISHED_TASKS.format(
                summary.finishedTasks.size,
                summary.finishedTasks.toString()
            )
        }

        return Message(summary.email, SUBJECT, text)
    }

    private data class Message(
        val recipient: String,
        val subject: String,
        val text: String,
    )
}
