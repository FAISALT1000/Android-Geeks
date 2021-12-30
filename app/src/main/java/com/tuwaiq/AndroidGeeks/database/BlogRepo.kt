package com.tuwaiq.AndroidGeeks.database

import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import java.text.SimpleDateFormat
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
    private val imageUrl=posts.postImageUrl




    fun addPost(userID:String,title:String,description:String,date:Date,photoUri: Uri){

        val postss=Posts(userID,title,description,date)
//        "${title.trim()}_from_${userID}"
        dataBase.collection("Posts").document(postss.id.toString()).set(postss)
        uploadImage(title, postss.id,photoUri)
        Log.d(TAG, "title: $title")
        Log.d(TAG, "postss.id: ${postss.id}")
        Log.d(TAG, "photoUri: $photoUri")
//        dataBase.collection("Posts").document(postss.id).delete()
//        dataBase.collection("Posts").document(postss.id).update("title","dd","age",15)
//
}

    fun addCommit(){

    }

    private fun uploadImage(title:String, postId: String,photoUri: Uri) {
//        val progressDialog= ProgressDialog(context)
//        progressDialog.setMessage("Uploading File.....")
//        progressDialog.setCancelable(false)
//        progressDialog.show()

        val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val todayDate=Date()
        val fileName=formatter.format(todayDate)
        val  storage= FirebaseStorage.getInstance()

        val storagee=storage.getReference("image/$title/$postId/$fileName")
        val uploadtask= storagee.putFile(photoUri!!)
        uploadtask.continueWithTask{task->
            if (!task.isSuccessful){
                task.exception?.let { throw it }
            }
            storagee.downloadUrl
        }.addOnCompleteListener { task->
            if (task.isSuccessful){
                task.result

                val ref = dataBase.collection("Posts").document(postId)

                ref
                    .update("postImageUrl", task.result.toString())
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            }
        }
        /*.addOnSuccessListener{ binding.postImageView.setImageURI(null)

        Toast.makeText(context,"Successfully Upload The Image",Toast.LENGTH_SHORT).show()

            if (progressDialog.isShowing) progressDialog.dismiss()
        }
        .addOnFailureListener{
            if (progressDialog.isShowing)progressDialog.dismiss()
            Toast.makeText(context,"Field",Toast.LENGTH_SHORT).show()
        }*/
        /**/

    }
    fun newUser(email:String,password:String) {
     auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { Log.d(TAG,"newUserRepoComplete")  }
         .addOnFailureListener { Log.d(TAG,"newUserRepoFailure")}}


    fun loginUser(email:String, password:String, isSuccessful:Boolean){
        val user = Firebase.auth.currentUser!!

        val credential = EmailAuthProvider
            .getCredential("$email", "$password")

        user.reauthenticate(credential)
            .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
        var isSuccessful1=isSuccessful
      val auth= auth.signInWithEmailAndPassword(email, password)
        auth.addOnFailureListener {}
              if (auth.isSuccessful) isSuccessful1=true

    }

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
//get the current user id
    fun getUserInfo(): Task<DocumentSnapshot>? {
       val usersInfo= userId?.let { dataBase.collection("UsersInfo").document(it).get() }
        return usersInfo
    }
}