package com.leeeqo.repository

import com.leeeqo.entity.Token
import com.leeeqo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Token, Long> {

    fun findByUser(user: User): Token?

    fun findByJwt(jwt: String): Token?
}