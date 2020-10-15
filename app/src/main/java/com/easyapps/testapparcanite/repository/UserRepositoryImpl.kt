package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.api.API
import com.easyapps.testapparcanite.api.ApiCreator
import com.easyapps.testapparcanite.dto.UserResponseDto
import com.easyapps.testapparcanite.model.User
import java.io.IOException

class UserRepositoryImpl : UserRepository {

    private val api: API = ApiCreator().api

    override suspend fun getAll(): List<User> {
        val response = api.getAllUsers()

        if (response.isSuccessful) {
            val userResponseDtoList: List<UserResponseDto> = requireNotNull(response.body())
            return userResponseDtoList.map(UserResponseDto.Companion::toModel)
        } else throw IOException()
    }

}