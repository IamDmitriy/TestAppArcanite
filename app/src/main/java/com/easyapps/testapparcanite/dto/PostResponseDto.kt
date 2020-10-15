package com.easyapps.testapparcanite.dto

import com.easyapps.testapparcanite.model.Post

data class PostResponseDto(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
) {
    companion object {
        fun toModel(dto: PostResponseDto): Post = Post(
            dto.userId,
            dto.id,
            dto.title,
            dto.body
        )
    }
}