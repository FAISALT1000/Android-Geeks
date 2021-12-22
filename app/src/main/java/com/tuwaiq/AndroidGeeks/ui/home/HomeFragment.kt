package com.tuwaiq.AndroidGeeks.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts

private const val TAG = "home"
class HomeFragment : Fragment() {

    private lateinit var blogRecyclerView: RecyclerView
    private lateinit var database:FirebaseFirestore
    private lateinit var adapter:PostAdapter
    private lateinit var posts:ArrayList<Posts>

    val homeViewModel by lazy{ ViewModelProvider(this)[HomeViewModel::class.java] }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       // Log.d(TAG,"${homeViewModel.getAllPost()}")

        val view= inflater.inflate(R.layout.fragment_home, container, false)



        blogRecyclerView=view.findViewById(R.id.blog_recyclerView)
        val linearLayoutManager= LinearLayoutManager(context)
        blogRecyclerView.layoutManager=linearLayoutManager
        posts= arrayListOf()
        adapter=PostAdapter(posts)
        blogRecyclerView.adapter=adapter

        database= FirebaseFirestore.getInstance()
        database.collection("Posts").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error !=null){
                    Log.e(TAG,error.message.toString())// if there are error
                }else{
                    for (dc:DocumentChange in value?.documentChanges!!){


                        if (dc.type == DocumentChange.Type.ADDED){
                            dc.document
//                            Log.d(TAG,"${ posts.add(dc.document.toObject(Posts::class.java))}")
                            Log.d(TAG,"${ dc.document.toString().toList()}")
//                            Log.d(TAG,"${ posts.trimToSize()}")
                            Log.d(TAG,"${adapter.post}")
//                            Log.d(TAG,"${ posts.size}")
//                            Log.d(TAG,"${posts}")



                        }}}}})


        return view
    }
    private inner class PostAdapter(var post:List<Posts>):RecyclerView.Adapter<PostViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view=layoutInflater.inflate(R.layout.list_view_item,parent,false)
            Log.d(TAG,"PostAdapter")
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post:Posts=posts[position]
            holder.postTitle.text=post.title
            holder.postDate.text=post.postDate.toString()
            holder.postDescription.text=post.description

            //   holder.postTitle.text=post.title
        }

        override fun getItemCount(): Int {
            return post.size
        }
    }

    private inner class PostViewHolder (view:View) : RecyclerView.ViewHolder(view) ,View.OnClickListener {

          val postImageView: ImageView=view.findViewById(R.id.post_image)
         val postTitle: TextView =view.findViewById(R.id.post_title_tv)
          val postDate:TextView =view.findViewById(R.id.date_tv)
          val postDescription:TextView =view.findViewById(R.id.postdec_tv)
       // private  var postLike:TextView


        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }


}