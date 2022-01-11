package com.tuwaiq.AndroidGeeks.comment


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.PostFragmentArgs
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.comments.Comments
import com.tuwaiq.AndroidGeeks.databinding.CommentFragmentBinding



class CommentFragment : Fragment() {
    private val args by navArgs<CommentFragmentArgs>()
    private lateinit var binding: CommentFragmentBinding
   // private lateinit var myAdapter:
  //  private lateinit var auth:FirebaseAuth
    private val database = FirebaseFirestore.getInstance()
    private lateinit var commentt:ArrayList<Comments>
    private val post=Posts()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  binding= PostFragmentBinding.inflate(layoutInflater)
        binding= CommentFragmentBinding.inflate(layoutInflater)
        return binding.root
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
            holder.commentText.text=comment.comment
            holder.commentDate.text=comment.commentDate.toString()
         //   holder.userImage.tooltipText = comment.userImage
            database.collection("Comment").document(comment.id.toString()).get()

        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }
/**/
    private inner class CommentViewHolder (view:View)
        : RecyclerView.ViewHolder(view),View.OnClickListener{
        val commentText:TextView=view.findViewById(R.id.comment_tv)
        val commentDate:TextView=view.findViewById(R.id.comment_datetv)
        val userImage:ImageView=view.findViewById(R.id.user_imageView)
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

}