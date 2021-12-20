package com.tuwaiq.AndroidGeeks.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo

class BlogRepo {
    private val dataBase=FirebaseFirestore.getInstance()
    private val auth=FirebaseAuth.getInstance()
    private val userId=auth.currentUser?.uid!!


    fun addPost(posts: Posts){
       dataBase.collection("Posts").add(posts)
    }

    fun newUser(email:String,password:String){

        auth.createUserWithEmailAndPassword(email, password)
    }

    fun loginUser(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
    }

    fun addUserInfo(usersInfo: UsersInfo){

        dataBase.collection("UsersInfo").document(userId).set(usersInfo)
    }
    fun getAllPost(){}

//    fun getUserInfo(usersInfo: UsersInfo):LiveData<List<UsersInfo>> {
//       val usersInfo= listOf (dataBase.collection("UsersInfo").document(userId).get())
//        return usersInfo
//    }
}