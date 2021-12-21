package com.tuwaiq.AndroidGeeks.newpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NewPostViewModel() : ViewModel() {
    private  var repo=BlogRepo()

    fun addNewPost(posts: Posts,isSessful:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repo.addPost(posts,isSessful)
            viewModelScope.launch {  }
        }
    }
}