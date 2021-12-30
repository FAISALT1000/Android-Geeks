package com.tuwaiq.AndroidGeeks.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SignupViewModel"
class SignupViewModel : ViewModel() {

    private  var repo= BlogRepo()


    fun newUser(email:String,password:String){
        Log.d(TAG,"newUser viewModel")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG,"newUser viewModelScope")
            repo.newUser(email, password) }.invokeOnCompletion { viewModelScope.launch {}}}

    fun addUserInfo(usersInfo: UsersInfo){
        viewModelScope.launch(Dispatchers.IO) {
        repo.addUserInfo(usersInfo)
        }.invokeOnCompletion { viewModelScope.launch {}}
    }

}