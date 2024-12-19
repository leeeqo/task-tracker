package com.leeeqo.controller

import com.leeeqo.dto.UserRequest
import com.leeeqo.dto.UserResponse
import com.leeeqo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.security.auth.callback.ConfirmationCallback.OK


@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun read(): ResponseEntity<List<UserResponse>> =
        ResponseEntity.ok(userService.read())

    @GetMapping("/{id}")
    fun readById(
        //@RequestHeader clientId: Long,
        @PathVariable("id") userId: Long
    ): ResponseEntity<UserResponse> =
        ResponseEntity.ok(userService.readById(userId))

    /*@GetMapping("/task/{id}")
    fun readByTaskId(
        //RequestHeader clientId: Long,
        @PathVariable("id") taskId: Long
    ): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.readByTask(taskId))
    }*/

    @PostMapping
    fun create(
        //@RequestHeader clientId: Long,
        @RequestBody @Valid userRequest: UserRequest
    ): ResponseEntity<UserResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequest))

    @DeleteMapping("/{id}")
    fun delete(
        //@RequestHeader clientId: Long,
        @PathVariable("id") userId: Long
    ): ResponseEntity<Boolean> =
        ResponseEntity.ok(userService.delete(userId))
}
