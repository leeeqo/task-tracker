package com.leeeqo.mapper

import com.leeeqo.dto.UserRequest
import com.leeeqo.dto.UserResponse
import com.leeeqo.entity.User
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

//@Mapper(componentModel = "spring")
@Component
class UserMapper { //: EntityMapper<User, UserRequest, UserResponse>

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
