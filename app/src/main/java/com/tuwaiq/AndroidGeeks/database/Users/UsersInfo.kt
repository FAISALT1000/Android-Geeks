package com.tuwaiq.AndroidGeeks.database.Users

import java.util.*


data class UsersInfo(
    val id:String="",
    val userName:String="",
    val firstName:String="",
    val lastName:String="",
    val phoneNumber: String="",
    var profileImageUrl: String ="",
    val gender:String="",
    val updateDate:Date= Date()
)
