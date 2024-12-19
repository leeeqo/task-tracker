package com.leeeqo.repository

import com.leeeqo.entity.TaskId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskIdRepository : JpaRepository<TaskId, Long> {

    fun existsByTaskIdAndCreatedById(taskId: Long, createdById: Long): Boolean
}
