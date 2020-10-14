package com.easyapps.testapparcanite.api

import com.easyapps.testapparcanite.dto.PostResponseDto
import com.easyapps.testapparcanite.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface API {

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserResponseDto>>

    @GET("posts")
    suspend fun getAllPosts(): Response<List<PostResponseDto>>

}
