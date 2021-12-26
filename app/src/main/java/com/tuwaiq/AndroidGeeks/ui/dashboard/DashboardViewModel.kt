package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.util.MalformedJsonException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.util.*

class DashboardViewModel(
    val userId:String,
    val userName:String,
    val firstName:String,
    val lastName:String,
    val phoneNumber:String,
    val updateDate: Date
) : ViewModel() {

    private lateinit var repo: BlogRepo
    private lateinit var auth:FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var jvmField: JvmField
    private lateinit var jsonException: MalformedJsonException
    private lateinit var group: ThreadGroup
    private lateinit var model: ViewModel
    private lateinit var typeNotPresentException: TypeNotPresentException
    private lateinit var data: LiveData<Posts>
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