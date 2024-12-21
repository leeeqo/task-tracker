package com.leeeqo.kafka

import com.leeeqo.dto.kafka.Operation
import com.leeeqo.dto.kafka.UserTaskMessage
import com.leeeqo.entity.UserId
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class UserIdProducer(
    @Value("\${spring.kafka.topics.user-update}")
    private var userUpdateTopic: String,

    private val kafkaTemplate: KafkaTemplate<String, UserTaskMessage>
) {

    @PostPersist
    fun postPersist(userId: UserId) = sendMessage(userId, Operation.PERSISTS)

    @PostRemove
    fun postRemove(userId: UserId) = sendMessage(userId, Operation.REMOVE)

    private fun sendMessage(userId: UserId, type: Operation) {
        kafkaTemplate.send(
            userUpdateTopic,
            UserTaskMessage(
                userId = userId.userId,
                taskId = userId.assignedTask.id,
                type = type
            )
        )
    }
}
