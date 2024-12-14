package com.leeeqo.service

import com.leeeqo.dto.TaskRequest
import com.leeeqo.entity.Task
import com.leeeqo.entity.User
import com.leeeqo.repository.TaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    fun getTasks(user: User): List<Task> = taskRepository.findAllByUser(user)

    fun createTask(user: User, request: TaskRequest): Task = Task(
            title = request.title,
            description = request.description,
            creationDate = LocalDateTime.now(),
            deadline = request.deadline,
            user = user
        ).apply {
            taskRepository.save(this)
        }

    fun deleteTask(taskId: Long) = taskRepository.deleteById(taskId)

    fun finishTask(taskId: Long): Boolean {
        val task = taskRepository.findById(taskId)
            .orElseThrow { Exception("Task by provided id not found") }

        task.completionDate = LocalDateTime.now()

        taskRepository.save(task)

        return true
    }
}
