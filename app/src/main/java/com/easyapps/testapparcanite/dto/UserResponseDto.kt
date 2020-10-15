package com.easyapps.testapparcanite.dto

import com.easyapps.testapparcanite.model.Address
import com.easyapps.testapparcanite.model.Company
import com.easyapps.testapparcanite.model.User

data class UserResponseDto(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) {
    companion object {
        fun toModel(dto: UserResponseDto) = User(
            dto.id,
            dto.name,
            dto.username,
            dto.email,
            dto.address,
            dto.phone,
            dto.website,
            dto.company
        )
    }
}