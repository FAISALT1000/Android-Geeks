package com.tuwaiq.AndroidGeeks.database.Post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize// like zip the file to extract later
data class Posts(
    var userId:String="",
    var title:String="",
    var description:String="",
    var postDate: Date=Date(),
    var postImageUrl: String ="",
    var id:String= UUID.randomUUID().toString()
   // val postLike:Int=0,
):Parcelable
