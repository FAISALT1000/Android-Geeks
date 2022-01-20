package com.tuwaiq.AndroidGeeks.database.comments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Comments(
    val id:String= UUID.randomUUID().toString(),
    val postId:String="",
    val userId:String="",
    val comment:String="",
    val commentDate: Date=Date(),
    val userName:String="",
): Parcelable
