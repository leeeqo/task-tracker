package com.leeeqo.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.leeeqo.dto.kafka.Operation
import com.leeeqo.dto.kafka.UserTaskMessage
import com.leeeqo.entity.Task
import com.leeeqo.entity.UserId
import com.leeeqo.repository.TaskRepository
import com.leeeqo.repository.UserIdRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskConsumer(
    private val userIdRepository: UserIdRepository,
    private val taskRepository: TaskRepository
) {

    companion object {
        const val TOPIC = "TASK_UPDATE"
     }

    @Transactional
    @KafkaListener(topics = [TOPIC], groupId = "task-tracker")
    fun listen(message: UserTaskMessage): Any =
        when (message.type) {
            Operation.PERSISTS ->
                if (!userIdRepository.existsByAssignedTaskIdAndUserId(message.taskId, message.userId)) {
                    userIdRepository.save(
                        UserId(
                            assignedTask = Task(
                                id = message.taskId
                            ),
                            userId = message.userId
                        )
                    )
                } else {
                    throw Exception("UserId relation from kafka already exists") //TODO
                }
            Operation.REMOVE ->
                taskRepository.findById(message.taskId)
                    .map { it.removeAssignee(message.userId) }
                    .ifPresent(taskRepository::saveAndFlush)
        }
}
