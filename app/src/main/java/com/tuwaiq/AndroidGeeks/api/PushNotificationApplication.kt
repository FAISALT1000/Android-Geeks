package com.tuwaiq.AndroidGeeks.api

import android.app.Application
import com.onesignal.OneSignal

class PushNotificationApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

    companion object{
        private const val ONESIGNAL_APP_ID="89a69544-f8bf-48ed-a664-991843aff826"
    }

}