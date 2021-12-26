package com.tuwaiq.AndroidGeeks.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "LoginViewModel"
class LoginViewModel:ViewModel() {
    private lateinit var database:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var repo:BlogRepo


   fun loginUser(email:String,password:String,isSesscful:Boolean){
       Log.d(TAG,"loginUser")
       repo=BlogRepo()
       viewModelScope.launch(Dispatchers.IO) {
           Log.d(TAG,"loginUser ViewModelScope")
           repo.loginUser(email, password,isSesscful)
       }.invokeOnCompletion { viewModelScope.launch{

       } }

   }



    fun addUserInfo(usersInfo: UsersInfo){
        repo.addUserInfo(usersInfo)
    }

    fun getAllPost(){
        repo.getAllPost()
    }

}



