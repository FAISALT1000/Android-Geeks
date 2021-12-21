package com.tuwaiq.AndroidGeeks.newpost

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.login.LoginViewModel
import java.util.*

class NewPostFragment : Fragment() {

    private lateinit var titleEt:EditText
    private lateinit var postEt:EditText
    private lateinit var postBtn:Button
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: NewPostViewModel
    private  var posts = Posts()
    private var repo=BlogRepo().addPost(posts,true)

    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[NewPostViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.new_post_fragment, container, false)
        val date=Date()
        titleEt=view.findViewById(R.id.title_tv)
        postEt=view.findViewById(R.id.post_et)
        postBtn=view.findViewById(R.id.post_btn)


        return view
    }

    override fun onStart() {
        super.onStart()


        postBtn.setOnClickListener {
            saveThePost()
        }


    }
    fun saveThePost(/*titleEt:Object,postEt:Object,date: Date*/){
        dataBase = FirebaseFirestore.getInstance()



        var post= fragmentViewModel.addNewPost(posts,true)
        val title=titleEt.text.toString()
        val description=postEt.text.toString()
        val userId= FirebaseAuth.getInstance().currentUser?.uid.toString()
        val date=Date()
        posts.userId= userId
        posts.title= title
        posts.description=description
        posts.postDate=date

       /* val post:MutableMap<String, Object> =HashMap()
        post["Title"] = titleEt
        post["Description"] = postEt
       // post["Date"] = date*/
        if (repo) {
//            dataBase.collection("posts")
//                .add(post)
//                .addOnSuccessListener {
//                    Toast.makeText(context,getString(R.string.success_toast),Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(context,getString(R.string.failure_toast),Toast.LENGTH_SHORT).show() }
       }
    }



}