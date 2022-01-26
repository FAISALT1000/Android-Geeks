package com.tuwaiq.AndroidGeeks.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG_DATE = "TAG_DATE"
private const val TAG = "home"
private const val TAG0 = "home0"
private const val TAG1 = "home fragment"
class HomeFragment : Fragment() {

    private  var userID=FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var blogRecyclerView: RecyclerView
    private lateinit var database:FirebaseFirestore
    private lateinit var myAdapter:PostAdapter
    private lateinit var posts:ArrayList<Posts>
   private val databaseV2 = FirebaseFirestore.getInstance()
    val myID = FirebaseAuth.getInstance().currentUser?.uid

    private  var postss: Posts=Posts()

    val homeViewModel by lazy{ ViewModelProvider(this)[HomeViewModel::class.java] }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_view_item2,parent,false)
            Log.d(TAG,"PostAdapter")
            return PostViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post:Posts=posts[position]
            holder.postId.text=post.id
            holder.userIId.text=post.userId
            holder.postTitle.text=post.title
            holder.test1(post)
            holder.imagePath = post.postImageUrl
            Log.d(TAG1, "${post.postImageUrl}")
            dateFormat(post.postDate)
            holder.postDate.text=dateFormat(post.postDate).toString()
            holder.postDescription.text=post.description
//            holder.itemView.setOnClickListener {
//                Toast.makeText(context, "${holder.postTitle}", Toast.LENGTH_SHORT).show()
//           //     findNavController().navigate(R.id.action_newPostFragment_to_navigation_notifications)
//            }
            //   holder.postTitle.text=post.title
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(myID.toString()).collection("Favorite")
                .document(post.id).get()

                .addOnCompleteListener {

                    if (it.result?.exists()!!) {     //Network Problem
                      //  Toast.makeText(holder.itemView.context, "like", Toast.LENGTH_SHORT).show()
                        holder.hart.setImageResource(R.drawable.ic_baseline_favorite_24)

                    } else {   //Network Problem

                       // Toast.makeText(holder.itemView.context, "no like", Toast.LENGTH_SHORT).show()  ///
                        holder.hart.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                    }   //Network Problem


                    holder.hart.setOnClickListener {
                        holder.upDateFavorite("${(postss.id)}", post)    //Network Problem
                    }
                }
//                .addOnFailureListener {
//                    holder.hart.visibility=View.GONE
//                }
        }

        override fun getItemCount(): Int {
            return post.size
        }

    }

    private inner class PostViewHolder (view:View)
        : RecyclerView.ViewHolder(view),View.OnClickListener{ /*,View.OnClickListener*/
        val hart:ImageView=view.findViewById(R.id.imageView2)
        val postId:TextView=view.findViewById(R.id.id_tv)
        val userIId:TextView=view.findViewById(R.id.userid_tv2)
        val postImageView: ImageView=view.findViewById(R.id.post_image_preview)
         val postTitle: TextView =view.findViewById(R.id.post_title_tv)
          val postDate:TextView =view.findViewById(R.id.date_tv)
          val postDescription:TextView =view.findViewById(R.id.postdec_tv)
        var imagePath:String = ""
        // private  var postLike:TextView
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        fun upDateFavorite(postID: String, post: Posts) {

            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(myID.toString()).collection("Favorite")
                .document(post.id).get()
                .addOnCompleteListener {
                    if (it.result?.exists()!!) {

                        deleteLike("${post.id}",post)
                        hart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        Toast.makeText(context, "remove Like", Toast.LENGTH_SHORT).show()

                    } else {

                        hart.setImageResource(R.drawable.ic_baseline_favorite_24)
                        addLike("${post.id}", post)
                        Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show()


                    }
                }
        }


        fun deleteLike(postID: String,post: Posts) {
            val deleteLikeFromThePost = FirebaseFirestore.getInstance()
            deleteLikeFromThePost.collection("Posts").document(post.id)
                .collection("Favorite").document(myID.toString()).delete()
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            Log.e(TAG, "Delete From Post Favorite")
                        }
                    }
                }

            val deleteLikeFromTheUsersInfo = FirebaseFirestore.getInstance()
            deleteLikeFromTheUsersInfo.collection("users").document(myID.toString())
                .collection("Favorite").document("${post.id}").delete()
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            Log.e(TAG, "Delete From User Favorite")
                        }
                    }
                }
            numberOfLikeInThePost(postID)
        }


        fun addLike(postID: String, post: Posts) {
            val addLike = hashMapOf(
                "PostID" to "${post.id}",
                "userId" to "${post.userId}",
            )
            //---------------------------------------------------------------------------------
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val postLike = Firebase.firestore.collection("users")
            postLike.document(userId.toString()).collection("Favorite")
                .document("${postID}")
                .set(addLike).addOnCompleteListener {
                    it
                    when {
                        it.isSuccessful -> {
                            Log.d(TAG, "Done")
                        }
                        else -> {
                            Log.d(TAG, " not Successful ")
                        }
                    }

                    //---------------------------------------------------------------------------------
                    val addLikeToThePosts = Firebase.firestore.collection("Posts")
                    addLikeToThePosts.document(postID.toString()).collection("Favorite")
                        .document("${userId.toString()}").set(addLike)



                }
        }
        fun numberOfLikeInThePost(postId: String) {
            databaseV2.collection("Posts").document(postId)
                .collection("Favorite").get()
                .addOnSuccessListener {
                    var numberOfLike = it.size()
                    Log.d("Like", "numberOfLikeInThePost: $numberOfLike ")
                    val userRef = Firebase.firestore.collection("Posts")
                    userRef.document("$postId").update("like", numberOfLike)
                    Log.d("Like", "numberOfLikeInThePost: $userRef ")}}
fun test1(post:Posts){


    postImageView.load(post.postImageUrl)

}

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {//weee
            val postss=Posts()//weee

            postss.id=postId.text.toString()
            postss.userId=userIId.text.toString()
            postss.title=postTitle.text.toString()//weee
            postss.description=postDescription.text.toString()//weee
            postss.postImageUrl=imagePath.toString()//weee
            val viww=HomeFragmentDirections.actionNavigationHomeToPostFragment(postss)//weee
         findNavController().navigate(viww)//weee
        }
    }
    private fun dateFormat(date: Date):Int{
        val dates = SimpleDateFormat("MM/dd/yyyy")
        var todaysDate: Date = Date()
        val currentDate = todaysDate
        val finalDate = date
        Log.d(TAG_DATE, "finalDate:$finalDate ")
        val date1_temp=dates.format(currentDate)

       // Log.d(TAG_DATE, "date2_temp:$date2_temp ")
        val date1=dates.parse(date1_temp)
        val  date2=finalDate
        Log.d(TAG_DATE, "date2:$date2 ")
        val difference: Long = (date1.time - date2.time)
        // val difference: Long = (finalDate.time - currentDate.time)
        val differenceDates = difference / ( 24 * 60 * 60 * 1000)
        Log.d(TAG_DATE, "differenceDates:$differenceDates ")
        val dayDifference = differenceDates.toInt()
        return dayDifference
    }

}