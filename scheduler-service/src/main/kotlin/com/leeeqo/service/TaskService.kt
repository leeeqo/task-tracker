package com.leeeqo.service

import com.leeeqo.dto.DailySummary
import com.leeeqo.dto.TaskDTO
import com.leeeqo.entity.Task
import com.leeeqo.entity.User
import com.leeeqo.repository.TaskRepository
import java.time.LocalDateTime

class TaskService(
    private val taskRepository: TaskRepository
) {

    companion object {
        const val TASKS_LIMIT = 5L
    }

    fun getDailySummaryByUser(user: User): DailySummary =
        DailySummary(
            user.email,
            getFinishedTasks(user),
            getUnfinishedTasks(user)
        )

    private fun getFinishedTasks(user: User): List<TaskDTO> =
        getTasksByUserAndPredicateWithLimit(user) {
            it.deadline > LocalDateTime.now().minusDays(1) && it.completionDate < it.deadline
        }

    private fun getUnfinishedTasks(user: User): List<TaskDTO> =
        getTasksByUserAndPredicateWithLimit(user) {
            it.deadline > LocalDateTime.now().minusDays(1) && it.completionDate > it.deadline
        }

    private fun getTasksByUserAndPredicateWithLimit(user: User, predicate: (Task) -> Boolean) =
        taskRepository.findAllByUser(user).stream()
            .filter { predicate(it) }
            .sorted(compareBy(Task::deadline))
            .map {
                TaskDTO(
                    title = it.title,
                    creationDate = it.creationDate,
                    deadline = it.deadline
                ) }
            .limit(TASKS_LIMIT)
            .toList()
}
