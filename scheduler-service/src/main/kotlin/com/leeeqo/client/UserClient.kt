package com.leeeqo.client

import com.leeeqo.dto.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "security-service-to-user-service-client", url = "user-service:8081/task-tracker")
interface UserClient {

    @GetMapping("/api/v1/users")
    fun receiveAll(): ResponseEntity<List<UserResponse>>
}
