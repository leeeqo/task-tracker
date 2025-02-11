package com.leeeqo.entity

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
data class Token (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @OneToOne
    @JoinColumn(name = "client_id")
    val client: Client = Client(),

    val jwt: String = "",
    val revoked: Boolean = false,
    val expired: Boolean = false
)
