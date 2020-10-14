package com.easyapps.testapparcanite.repository

import com.easyapps.testapparcanite.model.Post

interface PostRepository {
    suspend fun getMapAll(): Map<Long, List<Post>>
}