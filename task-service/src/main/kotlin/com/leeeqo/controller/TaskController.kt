package com.leeeqo.controller

import com.leeeqo.dto.request.TaskRequest
import com.leeeqo.entity.Task
import com.leeeqo.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getCreatedTasks(@RequestHeader clientId: Long): ResponseEntity<List<Task>> =
        ResponseEntity.ok(taskService.getTasksByClient(clientId))

    @PostMapping
    fun createTask(@RequestHeader clientId: Long,
                   @RequestBody request: TaskRequest
    ): ResponseEntity<Task> =
        ResponseEntity.ok(taskService.createTaskByClient(clientId, request))

    @PostMapping("/delete")
    fun deleteCreatedTask(@RequestHeader clientId: Long,
                          @RequestParam("taskId") taskId: Long
    ): ResponseEntity<Unit> =
        ResponseEntity.ok(taskService.deleteTaskByClient(clientId, taskId))

    @PostMapping("/finish")
    fun finishCreatedTask(@RequestHeader clientId: Long,
                          @RequestParam("taskId") taskId: Long
    ): ResponseEntity<Boolean> =
        ResponseEntity.ok(taskService.finishTaskByClient(clientId, taskId))
}
