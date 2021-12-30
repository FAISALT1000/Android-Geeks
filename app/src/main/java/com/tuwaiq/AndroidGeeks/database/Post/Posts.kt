package com.tuwaiq.AndroidGeeks.database.Post

import android.os.Parcelable
import com.google.firebase.storage.StorageReference
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Posts(
    var userId:String="",
    var title:String="",
    var description:String="",
    var postDate: Date=Date(),
    var postImageUrl: String ="",
    var id:String= UUID.randomUUID().toString()
   // val postLike:Int=0,
):Parcelable
