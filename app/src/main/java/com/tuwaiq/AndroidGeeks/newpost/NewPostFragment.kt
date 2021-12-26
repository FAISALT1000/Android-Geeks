package com.tuwaiq.AndroidGeeks.newpost

import android.content.Intent
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
import com.google.firebase.firestore.*
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.util.*

class NewPostFragment : Fragment() {

    private lateinit var titleEt:EditText
    private lateinit var postEt:EditText
    private lateinit var postBtn:Button
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private  val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var viewModel: NewPostViewModel
    private  var posts = Posts()


    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[NewPostViewModel::class.java] }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (userId==null){
                val intent = Intent(context, MainActivityForTesting::class.java)
                startActivity(intent)
                Toast.makeText(context, "you must sign in first", Toast.LENGTH_SHORT).show()

        }
        val view= inflater.inflate(R.layout.new_post_fragment, container, false)
        titleEt=view.findViewById(R.id.title_tv)
        postEt=view.findViewById(R.id.post_et)
        postBtn=view.findViewById(R.id.post_btn)


        return view}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context,"NEW POST",Toast.LENGTH_SHORT).show()
        super.onViewCreated(view, savedInstanceState)

        postBtn.setOnClickListener {
            addPost()

        }


    }
// To Add new Post
    private fun addPost() {
        val title = titleEt.text.toString()
        val description = postEt.text.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val date = Date()
        if (title.isNotEmpty() && description.isNotEmpty()) {
            var post = fragmentViewModel.addPost(userId,title, description, date)
  /*          posts.userId = userId
            posts.title = title
            posts.description = description
            posts.postDate = date*/
            Toast.makeText(context, getString(R.string.add_post_successful), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, getString(R.string.fill_the_field), Toast.LENGTH_SHORT).show()
        }
    }


   /* fun delete() {
        val title = titleEt.text.toString()
        val description = postEt.text.toString()
        val date = Date()
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val postss=Posts(userId, title,description,date)
/*this fun must be in the blogrepo not here */
        if (title.isNotEmpty() && description.isNotEmpty()) {
            dataBase= FirebaseFirestore.getInstance()
            dataBase.collection("Posts").document("${postss.title.trim()}_from_${postss.userId}").set(postss)
  /*         posts.userId = userId.
//            posts.title = titlegh
//            posts.description = description
//            posts.postDate = date posts.description = description
            */
            Toast.makeText(context, getString(R.string.add_post_successful), Toast.LENGTH_SHORT).show()
        }


    }































//    fun hi(){
//
//
//
//        database.collection("Posts").addSnapshotListener(object : EventListener<QuerySnapshot> {
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                if (error !=null){
//                    Log.e(TAG,error.message.toString())// if there are error
//                }else{
//                    for (dc: DocumentChange in value?.documentChanges!!){
//                        if (dc.type == DocumentChange.Type.ADDED){
//                            posts.add(dc.document.toObject(Posts::class.java))
//                        }
//                        myAdapter.notifyDataSetChanged()
//                    }
//
//                }}})
//
//
//
  }*/
}





