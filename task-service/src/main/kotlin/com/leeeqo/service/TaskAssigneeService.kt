package com.leeeqo.service

import com.leeeqo.client.UserClient
import com.leeeqo.dto.request.AssigneesRequest
import com.leeeqo.dto.response.TaskResponse
import com.leeeqo.mapper.TaskMapper
import com.leeeqo.repository.TaskRepository
import com.leeeqo.repository.UserIdRepository
import feign.FeignException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

private val kLogger = KotlinLogging.logger {}

@Service
class TaskAssigneeService(
    private val taskRepository: TaskRepository,
    private val userIdRepository: UserIdRepository,
    private val userClient: UserClient
) {

    fun getAllByAssigneeId(assigneeId: Long): List<TaskResponse> =
        userIdRepository.findAllByUserId(assigneeId).stream()
            .map { it.assignedTask }
            .map { TaskMapper.mapToResponse(it) }
            .toList()

    fun addAssignees(clientId: Long, taskId: Long, request: AssigneesRequest): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { Exception("Task with ID $taskId was not found") }

        if (task.clientId != clientId) {
            throw Exception("You don't have rights to finish this task (it was created by other user)")
        }

        for (assignee in request.assignees) {
            if (userIdRepository.existsByAssignedTaskIdAndUserId(task.id, assignee)) {
                kLogger.warn { "Task with ID ${task.id} is already assigned to user with ID $assignee" }
                continue
            }

            try {
                Optional.of(userClient.receiveUserById(assignee))
                    .map { it.body }
                    .ifPresent { task.addAssignee(it.id) }

                taskRepository.save(task)
            } catch (ex: FeignException.NotFound) {
                kLogger.warn { "User with ID $assignee was not found" }
            }
        }

        return TaskMapper.mapToResponse(task)
    }
}
