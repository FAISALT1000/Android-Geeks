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
    private val posts =Posts()
    private val userID = posts.userId
    private val title = posts.title
    private val description =posts.description
    private val date =posts.postDate



    fun addPost(userID:String,title:String,description:String,date:Date){
        viewModelScope.launch(Dispatchers.IO){
            repo.addPost(userID,title,description,date)
            viewModelScope.launch {  }
        }
    }
}