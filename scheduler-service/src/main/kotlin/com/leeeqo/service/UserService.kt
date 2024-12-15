package com.leeeqo.service

import com.leeeqo.entity.User
import com.leeeqo.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
) {

    fun getAllUsers(): List<User> = userRepository.findAll()
}
