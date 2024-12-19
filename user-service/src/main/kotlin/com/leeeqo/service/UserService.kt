package com.leeeqo.service

import com.leeeqo.dto.UserRequest
import com.leeeqo.dto.UserResponse
import com.leeeqo.mapper.UserMapper
import com.leeeqo.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    fun read(): List<UserResponse> =
        userRepository.findAll()
            .map(userMapper::mapToResponse)
            .toList()

    fun readById(userId: Long): UserResponse =
        userRepository.findById(userId)
            .map(userMapper::mapToResponse)
            .orElseThrow { Exception("User not found") } // TODO - exception

    /*fun readByTask(taskId: Long): List<UserResponse> =
        userRepository.findByTaskId(taskId)
            .map(userMapper::mapToResponse)
            // TODO - exception
            .orElseThrow { Exception("User not found") }

        fun create(userRequest: UserRequest): UserResponse {

    }*/

    fun create(userRequest: UserRequest): UserResponse? =
        if (userRepository.findByEmail(userRequest.email).isEmpty) {
            Optional.of(userRequest)
                .map(userMapper::mapToUser)
                .map(userRepository::save)
                .map(userMapper::mapToResponse)
                .orElseThrow { Exception("Something went wrong with saving user") }
        } else {
            throw Exception("User with email ${userRequest.email} already exists.")
        }

    fun delete(userId: Long): Boolean = userRepository.findById(userId)
        .map {
            userRepository.delete(it)
            it
        }
        .isPresent
}
