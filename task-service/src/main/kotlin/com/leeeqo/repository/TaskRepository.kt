package com.leeeqo.repository

import com.leeeqo.entity.Task
import com.leeeqo.entity.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findAllByClientId(clientId: Long): List<Task>
}
