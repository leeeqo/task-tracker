package com.leeeqo.client

import com.leeeqo.dto.TaskResponse
import com.leeeqo.dto.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

// TODO change port
@FeignClient(name = "security-service-to-task-service-client", url = "task-service:8081/task-tracker")
interface TaskClient {

    @GetMapping("/api/v1/tasks/{assigneeId}")
    fun receiveAllByUser(@PathVariable assigneeId: Long): ResponseEntity<List<TaskResponse>>
}
