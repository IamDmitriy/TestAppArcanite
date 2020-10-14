package com.easyapps.testapparcanite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easyapps.testapparcanite.model.User
import com.easyapps.testapparcanite.repository.PostRepository
import com.easyapps.testapparcanite.repository.PostRepositoryImpl
import com.easyapps.testapparcanite.repository.UserRepository
import com.easyapps.testapparcanite.repository.UserRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userRepository: UserRepository = UserRepositoryImpl()
        val postRepository: PostRepository = PostRepositoryImpl()
        launch {
            val userList:List<User> = userRepository.getAll()
            var output: String = ""
            userList.forEach {
                output+=it.name
            }


            val postMap = postRepository.getMapAll()
            postMap.forEach{
                it.value.forEach{
                    output += it.title
                }
            }

            tvTest.text = output
        }
    }
}
