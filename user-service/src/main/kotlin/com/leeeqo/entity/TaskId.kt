package com.leeeqo.entity

import com.leeeqo.kafka.KafkaProducer
import jakarta.persistence.*

@Entity
@Table(name = "task_ids")
@EntityListeners(KafkaProducer::class)
data class TaskId(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    val createdBy: User = User(),

    /*@ManyToMany
    @JoinTable(
        name = "task_ids_users",
        joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val assignedTo: List<User> = listOf(),*/

    val taskId: Long = 0L
)
