package com.tuwaiq.AndroidGeeks.newpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NewPostViewModel(val userId:String,val title: String,val description: String, val date:Date/*,val Likes:List<PostLike>*/) : ViewModel() {
    fun f(){
       // viewModelScope.launch(Dispatchers.IO)
    }
}