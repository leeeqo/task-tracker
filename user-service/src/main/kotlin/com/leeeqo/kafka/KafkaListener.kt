package com.leeeqo.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.leeeqo.dto.Operation
import com.leeeqo.dto.TaskUserMessage
import com.leeeqo.entity.TaskId
import com.leeeqo.entity.User
import com.leeeqo.repository.TaskIdRepository
import com.leeeqo.repository.UserRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaListener(
    private val userRepository: UserRepository,
    private val taskIdRepository: TaskIdRepository
) {

    companion object {
        const val TOPIC = "USER_UPDATE"

        val objectMapper = ObjectMapper()
    }

    @KafkaListener(topics = [TOPIC], groupId = "task-tracker")
    fun listen(message: TaskUserMessage): Any =
        when (message.type) {
            Operation.PERSISTS ->
                if (!taskIdRepository.existsByTaskIdAndCreatedById(message.taskId, message.userId)) {
                    taskIdRepository.save(
                        TaskId(
                            taskId = message.taskId,
                            createdBy = User(
                                id = message.userId
                            )
                        )
                    )
                } else {
                    throw Exception("Task from kafka already exists") //TODO
                }
            Operation.REMOVE ->
                userRepository.findById(message.userId)
                    .map { it.removeCreatedTaskId(message.taskId) }
                    .ifPresent(userRepository::saveAndFlush)
        }
}