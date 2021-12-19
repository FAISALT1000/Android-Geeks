package com.tuwaiq.blogerrtest.database.Post

import android.widget.ImageView
import com.tuwaiq.blogerrtest.newpost.PostLike
import java.util.*

data class Posts(
    val id:String="",
    val userId:String="",
    val title:String="",
    val description:String="",
    val postDate: Date,
    val postImageView: ImageView,
    val postLike:Int=0,
)
