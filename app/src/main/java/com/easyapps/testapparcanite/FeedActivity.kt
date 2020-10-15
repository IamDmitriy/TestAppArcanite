package com.easyapps.testapparcanite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.easyapps.testapparcanite.model.Post
import com.easyapps.testapparcanite.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(this)
        exListView.setAdapter(adapter)

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Start -> {
                    setUiStateStart()
                }
                is UiState.EmptyProgress -> {
                    setUiStateEmptyProgress()
                }
                is UiState.Success -> {
                    setUiStateSuccess(state.userList, state.postMap)
                }
                is UiState.Error -> {
                    setUiStateError()
                }
                is UiState.InternetAccessError -> {
                    setUiStateInternetAccessError()
                }
            }
        }

        btnTryLoadData.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setUiStateStart() {
        btnTryLoadData.visibility = View.VISIBLE
        tvInfoMessage.visibility = View.VISIBLE
        exListView.visibility = View.GONE
        progressBar.visibility = View.GONE

        tvInfoMessage.text = getString(R.string.upload_users_and_posts_message)
        btnTryLoadData.text = getString(R.string.upload)
    }

    private fun setUiStateEmptyProgress() {
        btnTryLoadData.visibility = View.GONE
        tvInfoMessage.visibility = View.GONE
        exListView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setUiStateSuccess(userList: List<User>, postMap: Map<Long, List<Post>>) {
        btnTryLoadData.visibility = View.GONE
        tvInfoMessage.visibility = View.GONE
        exListView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        adapter.setDataAndUpdate(userList, postMap)
    }

    private fun setUiStateError() {
        btnTryLoadData.visibility = View.VISIBLE
        tvInfoMessage.visibility = View.VISIBLE
        exListView.visibility = View.GONE
        progressBar.visibility = View.GONE

        tvInfoMessage.text = getString(R.string.error_message)
    }

    private fun setUiStateInternetAccessError() {
        btnTryLoadData.visibility = View.VISIBLE
        tvInfoMessage.visibility = View.VISIBLE
        exListView.visibility = View.GONE
        progressBar.visibility = View.GONE

        tvInfoMessage.text = getString(R.string.internet_access_error_message)
        btnTryLoadData.text = getString(R.string.try_again)
    }
}
