package com.leeeqo.service

import com.leeeqo.client.UserClient
import com.leeeqo.dto.UserResponse
import org.springframework.stereotype.Service

@Service
class UserService(
    //private val userRepository: UserRepository
    private val userClient: UserClient
) {

    fun getAllUsers(): List<UserResponse> =
        userClient.receiveAll().body ?: listOf()
}
