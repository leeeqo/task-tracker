package com.leeeqo.dto.response

import com.leeeqo.entity.UserId
import java.time.LocalDateTime

data class TaskResponse(
    val id: Long = 0L,

    val title: String = "",
    val description: String = "",

    val creationDate: LocalDateTime = LocalDateTime.now(),
    val deadline: LocalDateTime = LocalDateTime.now().plusYears(1),
    var completionDate: LocalDateTime = LocalDateTime.now().plusYears(1),

    val assignees: MutableList<UserId> = mutableListOf()
)
