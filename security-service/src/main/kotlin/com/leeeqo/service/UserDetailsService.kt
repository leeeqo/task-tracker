package com.leeeqo.service

import com.leeeqo.entity.Client
import com.leeeqo.exception.EmailNotFoundException
import com.leeeqo.repository.ClientRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val clientRepository: ClientRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): Client {
        val client = clientRepository.findByEmail(email)
            ?: throw EmailNotFoundException("User $email not found!")

        return Client(client.id, client.username, client.password)
    }
}
