package com.leeeqo.mapper

import com.leeeqo.dto.response.TaskResponse
import com.leeeqo.entity.Task

object TaskMapper {

    fun mapToResponse(task: Task): TaskResponse = with(task) {
        TaskResponse(
            id = id,
            title = title,
            description = description,
            creationDate = creationDate,
            deadline = deadline,
            completionDate = completionDate,
            assignees = assignees
        )
    }
}
