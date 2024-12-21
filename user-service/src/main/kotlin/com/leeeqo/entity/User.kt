package com.leeeqo.entity

import jakarta.persistence.*
import org.apache.commons.lang3.builder.ToStringExclude

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    //private val clientId: Long = 0L,

    val email: String = "",
    val phone: String = "",
    val name: String = "",

    @ToStringExclude
    @OneToMany(
        mappedBy = "assignee",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val assignedTasks: MutableList<TaskId> = mutableListOf(),

    /*@ToStringExclude
    @ManyToMany(mappedBy = "assignedTo")
    val assignedToTaskIds: MutableList<TaskId> = mutableListOf()*/
) {

    fun addAssignedTask(taskId: Long) = assignedTasks.add(
        TaskId(
            taskId = taskId,
            assignee = this
        )
    )

    fun removeAssignedTask(taskId: Long): User {
        assignedTasks.removeIf { it.taskId == taskId }

        return this
    }
}
