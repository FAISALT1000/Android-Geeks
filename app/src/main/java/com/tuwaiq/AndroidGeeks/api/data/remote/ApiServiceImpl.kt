package com.tuwaiq.AndroidGeeks.api.data.remote

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import okhttp3.OkHttpClient

class ApiServiceImpl(
    private val client: HttpClient
):ApiService {
    override suspend fun sendNotification(title: String, description: String) {
        try {
            client.get<String> {
                url(ApiService.SEND_NOTIFICATION)
                parameter("title",title)
                parameter("description",description)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}