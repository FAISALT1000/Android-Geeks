package com.tuwaiq.AndroidGeeks.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "home"
private const val TAG1 = "TAG"
const val KEY_ID="HomeFragment"
class HomeFragment : Fragment() {


    private lateinit var blogRecyclerView: RecyclerView
    private lateinit var database:FirebaseFirestore
    private lateinit var myAdapter:PostAdapter
    private lateinit var posts:ArrayList<Posts>

    private  var postss: Posts=Posts()

    val homeViewModel by lazy{ ViewModelProvider(this)[HomeViewModel::class.java] }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Log.d(TAG,"${homeViewModel.getAllPost()}")

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val imageUri=FirebaseStorage.getInstance().getReference()





        blogRecyclerView = view.findViewById(R.id.blog_recyclerView)
        val linearLayoutManager = LinearLayoutManager(context)
        blogRecyclerView.layoutManager = linearLayoutManager
        blogRecyclerView.setHasFixedSize(true)
        posts = arrayListOf()
        myAdapter = PostAdapter(posts)

        blogRecyclerView.adapter = myAdapter

        hi()
        return view
    }
        fun hi(){


        database= FirebaseFirestore.getInstance()
        database.collection("Posts").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error !=null){
                    Log.e(TAG,error.message.toString())// if there are error
                }else{
                    for (dc:DocumentChange in value?.documentChanges!!){


                        if (dc.type == DocumentChange.Type.ADDED){
                           // dc.document.data
                             posts.add(dc.document.toObject(Posts::class.java))
                           Log.d(TAG,"${ dc.document.data}")
                         //   Log.d(TAG,"${ dc.document.toString().toList()}")
//                            Log.d(TAG,"${ posts.trimToSize()}")
                           // Log.d(TAG,"${myAdapter.post}")
                        }
                        }
                    Log.d(TAG, "onCreateView: $posts")
                    myAdapter.notifyDataSetChanged()
                }}})



    }
    private inner class PostAdapter(var post:ArrayList<Posts>):RecyclerView.Adapter<PostViewHolder/**/>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            //val view=layoutInflater.from().inflate(R.layout.list_view_item,parent,false)
            val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_view_item2,parent,false)
            Log.d(TAG,"PostAdapter")
            return PostViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post:Posts=posts[position]
            holder.postId.text=post.id
            holder.postTitle.text=post.title
            holder.test1(post)
            holder.imagePath = post.postImageUrl
            Log.d(TAG1, "${post.postImageUrl}")
            val dates = SimpleDateFormat("MM/dd/yyyy")
            var todaysDate: Date = Date()
            val currentDate = todaysDate
            val finalDate = post.postDate
            val date1_temp=dates.format(currentDate)
            val  date2_temp=dates.format(finalDate)
            val date1=dates.parse(date1_temp)
            val  date2=dates.parse(date2_temp)
            val difference: Long = (date1.time - date2.time)
            // val difference: Long = (finalDate.time - currentDate.time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)
            val dayDifference = differenceDates.toInt()
            holder.postDate.text= dayDifference.toString()
            holder.postDescription.text=post.description
//            holder.itemView.setOnClickListener {
//                Toast.makeText(context, "${holder.postTitle}", Toast.LENGTH_SHORT).show()
//           //     findNavController().navigate(R.id.action_newPostFragment_to_navigation_notifications)
//            }
            //   holder.postTitle.text=post.title
        }

        override fun getItemCount(): Int {
            return post.size
        }

    }

    private inner class PostViewHolder (view:View)
        : RecyclerView.ViewHolder(view),View.OnClickListener{ /*,View.OnClickListener*/
        val postId:TextView=view.findViewById(R.id.id_tv)
        val postImageView: ImageView=view.findViewById(R.id.post_image_preview)
         val postTitle: TextView =view.findViewById(R.id.post_title_tv)
          val postDate:TextView =view.findViewById(R.id.date_tv)
          val postDescription:TextView =view.findViewById(R.id.postdec_tv)
        var imagePath:String = ""
        // private  var postLike:TextView

fun test1(post:Posts){


    postImageView.load(post.postImageUrl)

}/*
//        override fun onClick(v: View?) {
//            if(v==itemView) {
//                val args = Bundle()
//                args.putSerializable(KEY_ID, postss.title)
//            var dialog=UpdatePostDialog()
//                dialog.arguments=args
//                activity?
//                    dialog.show(this.parentFragmentManager, "Update Post")
//        }}
    */
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {//weee
            val postss=Posts()//weee

            postss.id=postId.text.toString()
            postss.title=postTitle.text.toString()//weee
            postss.description=postDescription.text.toString()//weee
            postss.postImageUrl=imagePath.toString()//weee
            Toast.makeText(context, "${imagePath}", Toast.LENGTH_SHORT).show()//weee
            val viww=HomeFragmentDirections.actionNavigationHomeToPostFragment(postss)//weee
         findNavController().navigate(viww)//weee
        }
    }

}