package com.tuwaiq.AndroidGeeks.comment


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener

import com.tuwaiq.AndroidGeeks.Post.POST_ID
import com.tuwaiq.AndroidGeeks.Post.POST_TEXT
import com.tuwaiq.AndroidGeeks.Post
.PostFragmentArgs
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.comments.Comments
import com.tuwaiq.AndroidGeeks.databinding.CommentFragmentBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CommentFragment"

class CommentFragment : Fragment() {

    private lateinit var binding: CommentFragmentBinding
    private lateinit var postId:String
    private lateinit var postText:String
    private val database = FirebaseFirestore.getInstance()
    private val auth=FirebaseAuth.getInstance()
    private val userId= auth.currentUser?.uid
    private lateinit var myAdapter: CommentAdapter
    private lateinit var commentt:ArrayList<Comments>
    private val commentId=Comments().id



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postId= requireArguments().getString(POST_ID).toString()
        postText=requireArguments().getString(POST_TEXT).toString()
        Log.d(TAG, "onCreateView: $postText")

        Log.d(TAG, "onCreateView: ${postId}")
        binding= CommentFragmentBinding.inflate(layoutInflater)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.postDecTv.text=postText
        binding.commentRecyclerview.layoutManager = linearLayoutManager
        binding.commentRecyclerview.setHasFixedSize(true)
        commentt = arrayListOf()
        myAdapter = CommentAdapter(commentt)
        binding.commentRecyclerview.adapter=myAdapter

        getTheCommentsFromFireBase()
        return binding.root
    }



    override fun onStart() {
        super.onStart()
      
        if (userId != null) {
            getUserName(userId)
            Log.d(TAG, "onStart:  ${getUserName(userId)}")
        }
        binding.imageButton.setOnClickListener {
            addComment()



        }
    }

    private fun addComment() {
        val comment = binding.inputET.text.toString()
        val userID = userId.toString()
        val userName = binding.textView3.text.toString()
        val id= commentId
        val comments = Comments(id = id,userId = userID, comment = comment, userName = userName, postId = postId)
        database.collection("Comments").document(id).set(comments)
    }

    private inner class CommentAdapter(var comments:ArrayList<Comments>):
        RecyclerView.Adapter<CommentFragment.CommentViewHolder/**/>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommentFragment.CommentViewHolder {
            val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_list_comment,parent,false)

            return CommentViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CommentFragment.CommentViewHolder, position: Int) {
           val comment:Comments=comments[position]
            holder.userName.text=comment.userName
            holder.commentText.text=comment.comment
            holder.commentDate.text=dateFormat(comment.commentDate).toString()
            database.collection("Comment").document(comment.id.toString()).get()


        }

        override fun getItemCount(): Int {return commentt.size}
    }
/**/
    private inner class CommentViewHolder (view:View)
        : RecyclerView.ViewHolder(view),View.OnClickListener{
        val commentText:TextView=view.findViewById(R.id.comment_tv)
        val commentDate:TextView=view.findViewById(R.id.comment_datetv)
        val userName:TextView=view.findViewById(R.id.user_name_tv)
        val commentId:String=Comments().id
        val commentUserId:String=Comments().userId
        val userImage:ImageView=view.findViewById(R.id.user_imageView)
    init {
        itemView.setOnClickListener(this)

    }
        override fun onClick(v: View?) {


                if (userId==commentUserId){
                    val d=database.collection("Comments").document(commentId).delete().addOnCompleteListener {
                    }.addOnFailureListener {

                    }
                    d}


        }
    }

    private fun getUserName(userId:String): String {
        var userName1:String=""
        database.collection("users").document(userId).get()
            .addOnCompleteListener {
                Log.d(TAG, "getUserName: OnCompleteListener")
                if (it.result!!.exists()){
                   binding.textView3.text=it.result!!.getString("userName").toString()
                    Log.d(TAG, "getUserName: ${userName1}#${userId}")
                }else{
                    Log.d(TAG, "getUserName: not exists")
                }
         }
        return userName1 }

    private fun getTheCommentsFromFireBase() {
       database.collection("Comments").whereEqualTo("postId","${postId}").addSnapshotListener(object : EventListener<QuerySnapshot>{
           override fun onEvent(value: QuerySnapshot?, someThingWrong: FirebaseFirestoreException?) {
               if (someThingWrong !=null){
                   Toast.makeText(context, "${someThingWrong.message}", Toast.LENGTH_SHORT).show()
               }
               for (dc:DocumentChange in value?.documentChanges!!){

                   if (dc.type==DocumentChange.Type.ADDED){
                       commentt.add(dc.document.toObject(Comments::class.java))

                   }
               }
               myAdapter.notifyDataSetChanged()
           }

       })
    }
    private fun dateFormat(date: Date):Int{
        val dates = SimpleDateFormat("MM/dd/yyyy")
        var todaysDate: Date = Date()
        val currentDate = todaysDate
        val finalDate = date

        val date1_temp=dates.format(currentDate)

        val date1=dates.parse(date1_temp)
        val  date2=finalDate

        val difference: Long = (date1.time - date2.time)
        val differenceDates = difference / ( 60 * 60 * 1000)
        val dayDifference = differenceDates.toInt()
        return dayDifference
    }

}