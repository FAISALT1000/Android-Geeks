package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.util.MalformedJsonException
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class DashboardViewModel() : ViewModel() {

    private  var repo=BlogRepo()
    private lateinit var auth:FirebaseAuth
   private lateinit var database: FirebaseFirestore

    fun addUserInfo(usersInfo: UsersInfo, UserId:String): LiveData<Boolean>{
        val task =  repo.addUserInfo(usersInfo,UserId)
        viewModelScope.launch(Dispatchers.IO) {
            task
        }.invokeOnCompletion { viewModelScope.launch {}}
        return liveData{try {task.await()
            if (task.isSuccessful){emit(true)}else{emit(false)}}catch (e: FirebaseFirestoreException){emit(false)}catch (e:Exception){
        }}
    }

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