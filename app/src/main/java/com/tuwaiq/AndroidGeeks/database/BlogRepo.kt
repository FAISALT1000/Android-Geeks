package com.tuwaiq.AndroidGeeks.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import java.util.*

private const val TAG = "BlogRepo"
class BlogRepo {
    private val dataBase=FirebaseFirestore.getInstance()
    private val auth=FirebaseAuth.getInstance()
    private val userId=auth.currentUser?.uid
        private val posts =Posts()
        private val userID = posts.userId
        private val title = posts.title
        private val description =posts.description
        private val date =posts.postDate



    fun addPost(userID:String,title:String,description:String,date:Date){
        val postss=Posts(userID,title,description,date)
        dataBase.collection("Posts").document("${title.trim()}_from_${userID}").set(postss)

     }

    fun addCommit(){

    }


    fun newUser(email:String,password:String) {
     auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { Log.d(TAG,"newUserRepoComplete")  }
         .addOnFailureListener { Log.d(TAG,"newUserRepoFailure")}}


    fun loginUser(email:String, password:String, isSuccessful:Boolean){
        var isSuccessful1=isSuccessful
      val auth= auth.signInWithEmailAndPassword(email, password)
              if (auth.isSuccessful) isSuccessful1=true}

    // user info
    fun addUserInfo(usersInfo: UsersInfo){
        if (userId != null) {
            dataBase.collection("UsersInfo").document(userId).set(usersInfo).addOnCompleteListener { Log.d(TAG,"addUserInfoRepoComplete") }
                .addOnFailureListener { Log.d(TAG,"addUserInfoRepoFailure") }}}


/*how to find one *///do
    fun getThePost(): Task<QuerySnapshot>{
    return dataBase.collection("Posts").orderBy("postDate").get()
    }
    fun getAllPost(): Task<QuerySnapshot> {
        return dataBase.collection("Posts").orderBy("postDate").get() }
//get the current user id jtfc vfor
    fun getUserInfo(): Task<DocumentSnapshot>? {
       val usersInfo= userId?.let { dataBase.collection("UsersInfo").document(it).get() }
        return usersInfo
    }
}