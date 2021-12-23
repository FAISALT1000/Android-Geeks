package com.tuwaiq.AndroidGeeks.newpost

import android.os.Bundle
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
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.util.*

class NewPostFragment : Fragment() {

    private lateinit var titleEt:EditText
    private lateinit var postEt:EditText
    private lateinit var postBtn:Button
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: NewPostViewModel
    private  var posts = Posts()


    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[NewPostViewModel::class.java] }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.new_post_fragment, container, false)
        val date=Date()
        titleEt=view.findViewById(R.id.title_tv)
        postEt=view.findViewById(R.id.post_et)
        postBtn=view.findViewById(R.id.post_btn)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context,"NEW POST",Toast.LENGTH_SHORT).show()
        super.onViewCreated(view, savedInstanceState)
        postBtn.setOnClickListener {
            val title=titleEt.text.toString()
            val description=postEt.text.toString()
            val userId= FirebaseAuth.getInstance().currentUser?.uid.toString()
            val date=Date()
            if(title.isNotEmpty()&& description.isNotEmpty()){
            var post= fragmentViewModel.addNewPost(posts,true)
            posts.userId= userId
            posts.title= title
            posts.description=description
            posts.postDate=date
                Toast.makeText(context,"add the post Successful",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Must fill all the field",Toast.LENGTH_SHORT).show()
            }

        }


    }
    }





