package com.tuwaiq.AndroidGeeks.database.Post

import com.google.firebase.storage.StorageReference
import java.util.*

data class Posts(

    var userId:String="",
    var title:String="",
    var description:String="",
    var postDate: Date=Date(),
    val postImageUrl: String ="",
   // val postLike:Int=0,
)
