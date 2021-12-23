package com.tuwaiq.AndroidGeeks.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DashboardViewModel(
    val userId:String,
    val userName:String,
    val firstName:String,
    val lastName:String,
    val phoneNumber:String,
    val updateDate: Date
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}
/*
 private val repo=BlogRepo()


    fun addUserInfo(usersInfo: UsersInfo){
        viewModelScope.launch {
            repo.addUserInfo(usersInfo)
        }.invokeOnCompletion {
            viewModelScope.launch {  }
        }
    }

 */