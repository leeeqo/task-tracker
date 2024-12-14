package com.leeeqo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: User = User(),
)
