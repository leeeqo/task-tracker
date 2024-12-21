package com.leeeqo.kafka

import com.leeeqo.dto.kafka.Operation
import com.leeeqo.dto.kafka.TaskUserMessage
import com.leeeqo.entity.TaskId
import com.leeeqo.entity.User
import com.leeeqo.repository.TaskIdRepository
import com.leeeqo.repository.UserRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserConsumer(
    private val userRepository: UserRepository,
    private val taskIdRepository: TaskIdRepository
) {

    companion object {
        const val TOPIC = "USER_UPDATE"
    }

    @Transactional
    @KafkaListener(topics = [TOPIC], groupId = "task-tracker")
    fun listen(message: TaskUserMessage): Any =
        when (message.type) {
            Operation.PERSISTS ->
                if (!taskIdRepository.existsByTaskIdAndAssigneeId(message.taskId, message.userId)) {
                    taskIdRepository.save(
                        TaskId(
                            assignee = User(
                                id = message.userId
                            ),
                            taskId = message.taskId
                        )
                    )
                } else {
                    throw Exception("Task relation from kafka already exists") //TODO
                }
            Operation.REMOVE ->
                userRepository.findById(message.userId)
                    .map { it.removeAssignedTask(message.taskId) }
                    .ifPresent(userRepository::saveAndFlush)
        }
}
