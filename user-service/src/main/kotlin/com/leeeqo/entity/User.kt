package com.leeeqo.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    //private val clientId: Long = 0L,

    val email: String = "",
    val phone: String = "",
    val name: String = ""
)