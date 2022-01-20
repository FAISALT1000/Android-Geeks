package com.tuwaiq.AndroidGeeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)
        val animation: LottieAnimationView =findViewById(R.id.lottieAnimationView)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivityForTesting::class.java)
            startActivity(intent)
            finish()
        }, 5200)
    }
}