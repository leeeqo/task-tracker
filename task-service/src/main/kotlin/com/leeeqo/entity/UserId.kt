package com.leeeqo.entity

import com.leeeqo.kafka.UserIdProducer
import jakarta.persistence.*

@Entity
@Table(name = "user_ids")
@EntityListeners(UserIdProducer::class)
class UserId(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    /*@OneToMany(
        mappedBy = "createdBy",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val createdTasks: MutableList<Task> = mutableListOf(),*/

    @ManyToOne
    @JoinColumn(name = "task_id")//, foreignKey = ForeignKey(name = "fk_user_ids_tasks"))
    val assignedTask: Task = Task(),

    val userId: Long = 0L,
)
