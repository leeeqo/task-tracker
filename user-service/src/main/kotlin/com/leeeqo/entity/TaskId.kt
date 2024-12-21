package com.leeeqo.entity

import com.leeeqo.kafka.TaskIdProducer
import jakarta.persistence.*

@Entity
@Table(name = "task_ids")
@EntityListeners(TaskIdProducer::class)
data class TaskId(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val assignee: User = User(),

    /*@ManyToMany
    @JoinTable(
        name = "task_ids_users",
        joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val assignedTo: List<User> = listOf(),*/

    val taskId: Long = 0L
)
