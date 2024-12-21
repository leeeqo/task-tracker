package com.leeeqo.entity

import jakarta.persistence.*
import org.apache.commons.lang3.builder.ToStringExclude
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val title: String = "",
    val description: String = "",

    val creationDate: LocalDateTime = LocalDateTime.now(),
    val deadline: LocalDateTime = LocalDateTime.now().plusYears(1),
    var completionDate: LocalDateTime = LocalDateTime.now().plusYears(1),

    /*@ToStringExclude
    @ManyToOne
    var createdBy: UserId = UserId(),*/

    @ToStringExclude
    @OneToMany(
        mappedBy = "assignedTask",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var assignees: MutableList<UserId> = mutableListOf(),

    val clientId: Long = 0L
) {

    fun addAssignee(userId: Long) {
        assignees.add(
            UserId(
                userId = userId,
                assignedTask = this
            )
        )
    }

    fun removeAssignee(userId: Long): Task {
        assignees.removeIf {
            it.userId == userId
        }

        return this
    }
}
