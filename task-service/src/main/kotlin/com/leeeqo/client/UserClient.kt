package com.leeeqo.client

import com.leeeqo.dto.response.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

//@Component
@FeignClient(name = "user-service", url = "user-service:8080/task-tracker/api/v1/users")
interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    fun receiveUserById(@PathVariable("id") userId: Long): ResponseEntity<UserResponse>
}
