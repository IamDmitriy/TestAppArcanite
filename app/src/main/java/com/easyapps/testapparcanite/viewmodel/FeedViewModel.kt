package com.easyapps.testapparcanite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyapps.testapparcanite.model.User
import com.easyapps.testapparcanite.repository.PostRepository
import com.easyapps.testapparcanite.repository.PostRepositoryImpl
import com.easyapps.testapparcanite.repository.UserRepository
import com.easyapps.testapparcanite.repository.UserRepositoryImpl
import kotlinx.coroutines.launch
import java.io.IOException

class FeedViewModel : ViewModel() {
    private val postRepository: PostRepository = PostRepositoryImpl()
    private val userRepository: UserRepository = UserRepositoryImpl()

    private val _uiState: MutableLiveData<UiState> =
        MutableLiveData()
    val uiState: LiveData<UiState>
        get() = _uiState

    init {
        _uiState.value = UiState.Start
    }

    fun loadData() {
        _uiState.value = UiState.EmptyProgress
        viewModelScope.launch {
            tryLoadData()
        }
    }

    private suspend fun tryLoadData() {
        _uiState.value = try {
            val userList: List<User> = userRepository.getAll()
            val postMap = postRepository.getMapAll()
            UiState.Success(userList, postMap)
        } catch (e: IOException) {
            UiState.InternetAccessError
        } catch (e: Exception) {
            UiState.Error
        }
    }
}