package com.tuwaiq.AndroidGeeks.database.Users

import java.util.*


data class UsersInfo(
    val id:String="",
    val userName:String="",
    val email: String="",
    var profileImageUrl: String ="",
    val updateDate:Date= Date()
)
