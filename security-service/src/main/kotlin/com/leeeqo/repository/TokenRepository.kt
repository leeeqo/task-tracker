package com.leeeqo.repository

import com.leeeqo.entity.Client
import com.leeeqo.entity.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Token, Long> {

    fun findByClient(client: Client): Token?

    fun findByJwt(jwt: String): Token?
}
