package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.model.User

interface UserRepository {
    suspend fun getAll(): List<User>
}