package com.leeeqo.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TaskRequest(
    val title: String = "",
    val description: String = "",

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    val deadline: LocalDateTime = LocalDateTime.MAX
)
