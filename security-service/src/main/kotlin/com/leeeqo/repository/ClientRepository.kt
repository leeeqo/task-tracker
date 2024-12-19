package com.leeeqo.repository

import com.leeeqo.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {

    fun findByEmail(email: String): Client?
}
