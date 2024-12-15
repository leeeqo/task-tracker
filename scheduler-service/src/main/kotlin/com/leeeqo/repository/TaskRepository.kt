package com.leeeqo.repository

import com.leeeqo.entity.Task
import com.leeeqo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findAllByUser(user: User): List<Task>
}
