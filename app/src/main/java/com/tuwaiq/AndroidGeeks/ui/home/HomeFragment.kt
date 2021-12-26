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
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.UpdatePostDialog
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "home"
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
                          //  dc.document
                              posts.add(dc.document.toObject(Posts::class.java))
//                            Log.d(TAG,"${ posts.add(dc.document.toObject(Posts::class.java))}")
                            Log.d(TAG,"${ dc.document.toString().toList()}")
//                            Log.d(TAG,"${ posts.trimToSize()}")
                            Log.d(TAG,"${myAdapter.post}")}
                        myAdapter.notifyDataSetChanged()}}}})



    }
    private inner class PostAdapter(var post:ArrayList<Posts>):RecyclerView.Adapter<PostViewHolder/**/>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            //val view=layoutInflater.from().inflate(R.layout.list_view_item,parent,false)
            val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_view_item,parent,false)
            Log.d(TAG,"PostAdapter")
            return PostViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post:Posts=posts[position]
            holder.postTitle.text=post.title
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

            //   holder.postTitle.text=post.title
        }

        override fun getItemCount(): Int {
            return post.size
        }
    }

    private inner class PostViewHolder (view:View)
        : RecyclerView.ViewHolder(view){ /*,View.OnClickListener*/

        val postImageView: ImageView=view.findViewById(R.id.post_image)
         val postTitle: TextView =view.findViewById(R.id.post_title_tv)
          val postDate:TextView =view.findViewById(R.id.date_tv)
          val postDescription:TextView =view.findViewById(R.id.postdec_tv)
       // private  var postLike:TextView


//        override fun onClick(v: View?) {
//            if(v==itemView) {
//                val args = Bundle()
//                args.putSerializable(KEY_ID, postss.title)
//            var dialog=UpdatePostDialog()
//                dialog.arguments=args
//                activity?
//                    dialog.show(this.parentFragmentManager, "Update Post")
//        }}
    }

}