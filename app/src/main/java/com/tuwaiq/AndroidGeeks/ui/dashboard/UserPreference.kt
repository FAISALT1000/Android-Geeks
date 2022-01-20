package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.content.Context

import androidx.preference.PreferenceManager


const val THEME_KEY="customTheme";

//;
object UserPreference {

    // this method will save the nightMode State : True or False
    fun setNightModeState(context: Context, state: Boolean) {
      PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(THEME_KEY,state).apply()
    }

    // this method will load the Night Mode State
    fun loadNightModeState(context: Context): Boolean {
        val preferencesM =PreferenceManager.getDefaultSharedPreferences(context)

        return preferencesM.getBoolean(THEME_KEY,false)
    }

}

//class UserPreference(context: Context) {
//    private var mySharedPref: SharedPreferences = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
//    //
//    // this method will save the nightMode State : True or False
//    fun setNightModeState(state: Boolean?) {
//        val editor = mySharedPref.edit()
//        editor.putBoolean(THEME_KEY, state!!)
//        editor.apply()
//    }
//
//    // this method will load the Night Mode State
//    fun loadNightModeState(): Boolean {
//        return mySharedPref.getBoolean(THEME_KEY, false)
//    }
//
//}
