package com.leeeqo.service

import com.leeeqo.dto.request.TaskRequest
import com.leeeqo.entity.Task
import com.leeeqo.repository.TaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    fun getTasksByClient(clientId: Long): List<Task> =
        taskRepository.findAllByClientId(clientId)

    fun createTaskByClient(clientId: Long, request: TaskRequest): Task = Task(
            title = request.title,
            description = request.description,
            creationDate = LocalDateTime.now(),
            deadline = request.deadline,
            clientId = clientId
        ).apply {
            taskRepository.save(this)
        }

    fun deleteTaskByClient(clientId: Long, taskId: Long) {
        val task = taskRepository.findById(taskId)
            .orElseThrow { Exception("Task with ID $taskId was not found") }

        if (task.clientId != clientId) {
            throw Exception("You don't have rights to delete this task (it was created by other user)")
        }

        taskRepository.delete(task)
    }

    fun finishTaskByClient(clientId: Long, taskId: Long): Boolean {
        val task = taskRepository.findById(taskId)
            .orElseThrow { Exception("Task with ID $taskId was not found") }

        if (task.clientId != clientId) {
            throw Exception("You don't have rights to finish this task (it was created by other user)")
        }

        task.completionDate = LocalDateTime.now()

        taskRepository.save(task)

        return true
    }
}
