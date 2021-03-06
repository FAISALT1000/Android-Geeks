package com.tuwaiq.AndroidGeeks.newpost

import android.net.Uri
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




    fun addPost(userID:String,title:String,description:String,date:Date,photoUri:Uri){
        viewModelScope.launch(Dispatchers.IO){
            repo.addPost(userID,title,description,date,photoUri)
            viewModelScope.launch {  }
        }
    }
}