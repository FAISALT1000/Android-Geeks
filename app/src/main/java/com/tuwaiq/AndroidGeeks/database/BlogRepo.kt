package com.tuwaiq.AndroidGeeks.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo

private const val TAG = "BlogRepo"
class BlogRepo {
    private val dataBase=FirebaseFirestore.getInstance()
    private val Auth=FirebaseAuth.getInstance()
    private val userId=Auth.currentUser?.uid


    fun addPost(posts: Posts, isSuccessful:Boolean):Boolean{
       var isSuccessful1=isSuccessful
       dataBase.collection("Posts").add(posts)
           .addOnCompleteListener { isSuccessful1=true }
           .addOnFailureListener { isSuccessful1=false}
        return isSuccessful}


    fun newUser(email:String,password:String) {
     Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { Log.d(TAG,"newUserRepoComplete")  }
         .addOnFailureListener { Log.d(TAG,"newUserRepoFailure")}}


    fun loginUser(email:String, password:String, isSuccessful:Boolean){
        var isSuccessful1=isSuccessful
      val auth= Auth.signInWithEmailAndPassword(email, password)
              if (auth.isSuccessful) isSuccessful1=true}

    // user info
    fun addUserInfo(usersInfo: UsersInfo){
        if (userId != null) {
            dataBase.collection("UsersInfo").document(userId).set(usersInfo).addOnCompleteListener { Log.d(TAG,"addUserInfoRepoComplete") }
                .addOnFailureListener { Log.d(TAG,"addUserInfoRepoFailure") }}}



    fun getAllPost(): Task<QuerySnapshot> {
        return dataBase.collection("Posts").get() }

//    fun getUserInfo(usersInfo: UsersInfo):LiveData<List<UsersInfo>> {
//       val usersInfo= listOf (dataBase.collection("UsersInfo").document(userId).get())
//        return usersInfo
//    }
}