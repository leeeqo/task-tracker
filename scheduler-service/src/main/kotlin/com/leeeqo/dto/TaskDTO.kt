package com.leeeqo.dto

import java.time.LocalDateTime

class TaskDTO(
    val title: String,
    val creationDate: LocalDateTime,
    val deadline: LocalDateTime
)
