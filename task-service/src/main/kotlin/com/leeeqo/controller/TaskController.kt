package com.leeeqo.controller

import com.leeeqo.dto.TaskRequest
import com.leeeqo.entity.Task
import com.leeeqo.entity.User
import com.leeeqo.service.TaskService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @GetMapping
    fun getTasks(@AuthenticationPrincipal user: User): ResponseEntity<List<Task>> =
        ResponseEntity.ok(taskService.getTasks(user))

    @PostMapping
    fun createTask(@AuthenticationPrincipal user: User,
                   @RequestBody request: TaskRequest
    ): ResponseEntity<Task> =
        ResponseEntity.ok(taskService.createTask(user, request))

    @PostMapping("/delete")
    fun deleteTask(@AuthenticationPrincipal user: User,
                   @RequestParam("taskId") taskId: Long
    ): ResponseEntity<Unit> =
        ResponseEntity.ok(taskService.deleteTask(taskId))

    @PostMapping("/finish")
    fun finishTask(@AuthenticationPrincipal user: User,
                   @RequestParam("taskId") taskId: Long
    ): ResponseEntity<Boolean> =
        ResponseEntity.ok(taskService.finishTask(taskId))
}
