package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.ui.dashboard.UserPreference
//
//class J : AppCompatActivity() {
//    private var myswitch: Switch? = null
//    var sharedpref: UserPreference? = null
//     override fun onCreate(savedInstanceState: Bundle?) {
//        sharedpref = SharedPref(this)
//        if (sharedpref.loadNightModeState() === true) {
//            setTheme(R.style.darktheme)
//        } else setTheme(R.style.AppTheme)
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_setting)
//        myswitch = findViewById(R.id.myswitch) as Switch?
//        if (sharedpref.loadNightModeState() === true) {
//            myswitch!!.isChecked = true
//        }
//        myswitch!!.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                sharedpref.setNightModeState(true)
//                restartApp()
//            } else {
//                sharedpref.setNightModeState(false)
//                restartApp()
//            }
//        }
//    }
//
//    fun restartApp() {
//        val i = Intent(getApplicationContext(), Setting::class.java)
//        startActivity(i)
//        finish()
//    }
//}