package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.api.API
import com.easyapps.testapparcanite.api.ApiCreator
import com.easyapps.testapparcanite.dto.PostResponseDto
import com.easyapps.testapparcanite.model.Post
import java.io.IOException

class PostRepositoryImpl : PostRepository {
    private val api: API = ApiCreator().api

    override suspend fun getMapAll(): Map<Long, List<Post>> {
        val response = api.getAllPosts()

        if (response.isSuccessful) {
            val postResponseDtoList: List<PostResponseDto> = requireNotNull(response.body())
            val postList = postResponseDtoList.map(PostResponseDto.Companion::toModel)
            val postMap: MutableMap<Long, MutableList<Post>> = mutableMapOf()

            postList.forEach {
                if (postMap.containsKey(it.userId)) {
                    postMap[it.userId]!!.add(it)
                } else {
                    postMap[it.userId] = mutableListOf(it)
                }
            }
            return postMap
        } else throw IOException()
    }
}