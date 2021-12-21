package com.tuwaiq.AndroidGeeks.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts

private const val TAG = "home"
class HomeFragment : Fragment() {

    private lateinit var blogRecyclerView: RecyclerView
    private lateinit var database:FirebaseFirestore
    private val posts=Posts()

    val homeViewModel by lazy{ ViewModelProvider(this)[HomeViewModel::class.java] }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"${homeViewModel.getAllPost()}")
       // Log.d(TAG,"${FirebaseFirestore.getInstance().collection("Posts").document().collection()}")

        val view= inflater.inflate(R.layout.fragment_home, container, false)

//        database.collection("posts").get()

        blogRecyclerView=view.findViewById(R.id.blog_recyclerView)
        val linearLayoutManager= LinearLayoutManager(context)
        blogRecyclerView.layoutManager=linearLayoutManager


        return view
    }
    private inner class PostViewHolder (view:View) : RecyclerView.ViewHolder(view) ,View.OnClickListener {
        private lateinit var postImageView: ImageView
        private lateinit var postTitle: TextView
        private lateinit var postDate:TextView
        private lateinit var postDescription:TextView
        private lateinit var postLike:TextView
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
    private inner class PostAdapter(var home:List<Home>):RecyclerView.Adapter<PostViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view=layoutInflater.inflate(R.layout.list_view_item,parent,false)
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }

}