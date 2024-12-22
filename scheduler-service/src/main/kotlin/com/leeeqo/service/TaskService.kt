package com.leeeqo.service

import com.leeeqo.client.TaskClient
import com.leeeqo.dto.DailySummary
import com.leeeqo.dto.TaskDTO
import com.leeeqo.dto.TaskResponse
import com.leeeqo.dto.UserResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val kLogger = KotlinLogging.logger {}

@Service
class TaskService(
    //private val taskRepository: TaskRepository
    private val taskClient: TaskClient
) {

    companion object {
        const val TASKS_LIMIT = 5L
    }

    fun getDailySummaryByUser(user: UserResponse): DailySummary =
        DailySummary(
            user.email,
            getFinishedTasks(user),
            getUnfinishedTasks(user)
        ).also {
            kLogger.info { "SCHEDULER: daily summary for ${user.email} prepared" }
        }

    private fun getFinishedTasks(user: UserResponse): List<TaskDTO> =
        getTasksByUserAndPredicateWithLimit(user) {
            it.deadline > LocalDateTime.now().minusDays(1) && it.completionDate < it.deadline
        }

    private fun getUnfinishedTasks(user: UserResponse): List<TaskDTO> =
        getTasksByUserAndPredicateWithLimit(user) {
            it.deadline > LocalDateTime.now().minusDays(1) && it.completionDate > it.deadline
        }

    private fun getTasksByUserAndPredicateWithLimit(user: UserResponse, predicate: (TaskResponse) -> Boolean): List<TaskDTO> {
        val tasks = taskClient.receiveAllByUser(user.id).body ?: listOf()

        return tasks.stream()
            .filter { predicate(it) }
            .sorted(compareBy(TaskResponse::deadline))
            .map {
                TaskDTO(
                    title = it.title,
                    creationDate = it.creationDate,
                    deadline = it.deadline
                )
            }
            .limit(TASKS_LIMIT)
            .toList()
    }
}
