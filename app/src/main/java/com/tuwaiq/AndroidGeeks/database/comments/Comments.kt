package com.tuwaiq.AndroidGeeks.database.comments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Comments(
    val id:UUID= UUID.randomUUID(),
    val postId:String="",
    val userId:String="",
    val comment:String="",
    val commentDate: Date=Date(),
    val userImage:String="",
    val userName:String="",
): Parcelable
