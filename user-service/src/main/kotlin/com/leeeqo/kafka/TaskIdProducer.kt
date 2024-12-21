package com.leeeqo.kafka

import com.leeeqo.dto.kafka.Operation
import com.leeeqo.dto.kafka.TaskUserMessage
import com.leeeqo.entity.TaskId
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class TaskIdProducer(
    @Value("\${spring.kafka.topics.task-update}")
    private var taskUpdateTopic: String,

    private val kafkaTemplate: KafkaTemplate<String, TaskUserMessage>
) {

    @PostRemove
    fun postRemove(taskId: TaskId) = sendMessage(taskId, Operation.REMOVE)

    @PostPersist
    fun postPersist(taskId: TaskId) = sendMessage(taskId, Operation.PERSISTS)

    private fun sendMessage(taskId: TaskId, type: Operation) {
        kafkaTemplate.send(
            taskUpdateTopic,
            TaskUserMessage(
                taskId = taskId.taskId,
                userId = taskId.assignee.id,
                type = type
            )
        )
    }
}
