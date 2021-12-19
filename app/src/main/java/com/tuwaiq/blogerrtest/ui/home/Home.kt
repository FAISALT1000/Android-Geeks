package com.tuwaiq.blogerrtest.ui.home

import android.widget.ImageView
import com.tuwaiq.blogerrtest.newpost.PostLike
import java.util.*

data class Home (
    val title:String="",
    val description:String="",
    val postDate: Date,
    val postImageView: ImageView,
    val postLike: PostLike,
        )