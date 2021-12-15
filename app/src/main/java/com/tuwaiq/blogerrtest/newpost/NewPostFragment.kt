package com.tuwaiq.blogerrtest.newpost

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.blogerrtest.R
import kotlinx.coroutines.currentCoroutineContext
import java.util.*
import kotlin.collections.HashMap

class NewPostFragment : Fragment() {

    private lateinit var titleEt:EditText
    private lateinit var postEt:EditText
    private lateinit var postBtn:Button
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: NewPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.new_post_fragment, container, false)
        val date=Date()
        titleEt=view.findViewById(R.id.title_tv)
        postEt=view.findViewById(R.id.post_et)
        postBtn=view.findViewById(R.id.post_btn)

        postBtn.setOnClickListener {
            val title=titleEt.text.toString()
            val thePost=postEt.text.toString()
            saveThePost()
        }

        if (titleEt.text.isNotEmpty()){
/* postBtn.isClickable=true */

       }

        return view
    }
    fun saveThePost(/*titleEt:Object,postEt:Object,date: Date*/){
        dataBase = FirebaseFirestore.getInstance()

        val title=titleEt.text.toString().trim()
        val description=postEt.text.toString().trim()
        val userId= auth.currentUser.toString()
        val date=Date()

        var post=NewPostViewModel(userId,title,description,date)
       /* val post:MutableMap<String, Object> =HashMap()
        post["Title"] = titleEt
        post["Description"] = postEt
       // post["Date"] = date*/
        dataBase.collection("posts")
            .add(post)
            .addOnSuccessListener {
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show() }
    }



}