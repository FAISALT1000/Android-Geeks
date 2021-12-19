package com.tuwaiq.blogerrtest.database.Users

import com.google.type.Date

data class UsersInfo(
    val id:String="",
    val userName:String="",
    val firstName:String="",
    val lastName:String="",
    val phoneNumber: Number=0,
    val gender:Boolean=false,
    val updateDate: Date
)
