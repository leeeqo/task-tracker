package com.leeeqo.dto.kafka

data class UserTaskMessage(
    val userId: Long,
    val taskId: Long,
    val type: Operation
)
