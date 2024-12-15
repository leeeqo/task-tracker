package com.leeeqo.dto

data class DailySummary(
    val email: String,
    val finishedTasks: List<TaskDTO>,
    val unfinishedTasks: List<TaskDTO>
)
