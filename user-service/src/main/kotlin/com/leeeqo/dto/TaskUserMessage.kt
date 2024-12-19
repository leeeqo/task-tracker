package com.leeeqo.dto

data class TaskUserMessage(
    val userId: Long,
    val taskId: Long,
    val type: Operation
)