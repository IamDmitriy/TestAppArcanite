package com.easyapps.testapparcanite

import com.easyapps.testapparcanite.model.Post
import com.easyapps.testapparcanite.model.User

sealed class UiState {
    object Start: UiState()

    object EmptyProgress : UiState()

    data class Success(val userList: List<User>, val postMap: Map<Long, List<Post>>) : UiState()

    object Error: UiState()

    object InternetAccessError : UiState()
}