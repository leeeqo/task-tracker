package com.leeeqo.dto.kafka

data class TaskUserMessage(
    val userId: Long,
    val taskId: Long,
    val type: Operation
)
