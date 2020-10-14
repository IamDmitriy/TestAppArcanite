package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.api.API
import com.easyapps.testapparcanite.api.Client
import com.easyapps.testapparcanite.dto.PostResponseDto
import com.easyapps.testapparcanite.model.Post
import java.io.IOException

class PostRepositoryImpl : PostRepository {
    private val api: API = Client().api

    override suspend fun getMapAll(): Map<Long, List<Post>> {
        val response = api.getAllPosts()
        val postList: List<Post>
        val postMap: MutableMap<Long, MutableList<Post>> = mutableMapOf()

        if (response.isSuccessful) {
            val postResponseDtoList: List<PostResponseDto> = requireNotNull(response.body())
            postList = postResponseDtoList.map(PostResponseDto.Companion::toModel)

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