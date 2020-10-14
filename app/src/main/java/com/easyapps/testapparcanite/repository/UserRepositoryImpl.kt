package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.api.API
import com.easyapps.testapparcanite.api.Client
import com.easyapps.testapparcanite.dto.UserResponseDto
import com.easyapps.testapparcanite.model.User
import java.io.IOException

class UserRepositoryImpl : UserRepository {

    private val api: API = Client().api

    override suspend fun getAll(): List<User> {
        val response = api.getAllUsers()

        if (response.isSuccessful) {
            val postResponseDtoList: List<UserResponseDto> = requireNotNull(response.body())
            return postResponseDtoList.map(UserResponseDto.Companion::toModel)
        } else throw IOException()
    }

}