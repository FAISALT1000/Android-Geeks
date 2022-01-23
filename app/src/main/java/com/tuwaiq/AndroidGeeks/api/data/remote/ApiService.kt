package com.tuwaiq.AndroidGeeks.api.data.remote

interface ApiService {

    suspend fun sendNotification(title:String,description:String)

    companion object{
        const val SEND_NOTIFICATION="http://192.168.43.53:8080/sendNotification"
    }
}