package com.leeeqo.mapper

import com.leeeqo.dto.UserRequest
import com.leeeqo.dto.UserResponse
import com.leeeqo.entity.User
import org.springframework.stereotype.Component

@Component
object UserMapper {

    fun mapToResponse(user: User) =
        UserResponse(
            name = user.name,
            email = user.email,
            phone = user.phone
        )

    fun mapToUser(userRequest: UserRequest) =
        User(
            name = userRequest.name,
            email = userRequest.email,
            phone = userRequest.phone
        )
}
