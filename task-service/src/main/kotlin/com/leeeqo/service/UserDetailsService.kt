package com.leeeqo.service

import com.leeeqo.entity.User
import com.leeeqo.exception.EmailNotFoundException
import com.leeeqo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw EmailNotFoundException("User $email not found!")

        return User(user.id, user.username, user.password)
    }
}
