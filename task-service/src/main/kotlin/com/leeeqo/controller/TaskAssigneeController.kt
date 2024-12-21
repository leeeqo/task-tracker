package com.leeeqo.controller

import com.leeeqo.dto.request.AssigneesRequest
import com.leeeqo.dto.response.TaskResponse
import com.leeeqo.service.TaskAssigneeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/tasks")
class TaskAssigneeController(
    private val taskAssigneeService: TaskAssigneeService
) {

    @PostMapping("/{taskId}/assignees")
    fun addAssignees(@RequestHeader clientId: Long,
                     @PathVariable("taskId") taskId: Long,
                     @RequestBody @Valid request: AssigneesRequest
    ): ResponseEntity<TaskResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(taskAssigneeService.addAssignees(clientId, taskId, request))
}
