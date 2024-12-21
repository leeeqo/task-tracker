package com.leeeqo.repository

import com.leeeqo.entity.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserIdRepository : JpaRepository<UserId, Long> {

    fun existsByAssignedTaskIdAndUserId(assignedTaskId: Long, userId: Long): Boolean
}
