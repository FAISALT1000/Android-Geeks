package com.tuwaiq.AndroidGeeks.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo

class LoginViewModel:ViewModel() {
    private lateinit var database:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var repo:BlogRepo


   fun loginUser(email:String,password:String){
       repo.loginUser(email,password)
   }

    fun newUser(email:String,password:String){
        repo.newUser(email, password)
    }

    fun addPost(posts: Posts){
        repo.addPost(posts)
    }

    fun addUserInfo(usersInfo: UsersInfo){
        repo.addUserInfo(usersInfo)
    }

    fun getAllPost(){
        repo.getAllPost()
    }

}



