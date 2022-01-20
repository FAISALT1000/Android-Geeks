package com.tuwaiq.AndroidGeeks.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAGe = "SignupViewModel"
private const val TAG = "ViewModel"
class SignupViewModel : ViewModel() {

    private  var repo= BlogRepo()


  /*  fun newUser(email:String,password:String){
        Log.d(TAG,"newUser viewModel")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG,"newUser viewModelScope")
            repo.newUser(email, password) }.invokeOnCompletion { viewModelScope.launch {}}}*/

    fun addUserInfo(usersInfo: UsersInfo,UserId:String): LiveData<Boolean>{
        val task =  repo.addUserInfo(usersInfo,UserId)
        viewModelScope.launch(Dispatchers.IO) {
        task
        }.invokeOnCompletion { viewModelScope.launch {}}
        return liveData{try {task.await()
            if (task.isSuccessful){emit(true)}else{emit(false)}}catch (e:FirebaseFirestoreException){emit(false)}catch (e:Exception){Log.e(TAGe, "loginUser: $e ", e)}}}



    fun loginUser(email:String,password:String): LiveData<Boolean> {
        val task =  repo.loginUser(email,password)
        viewModelScope.launch(Dispatchers.IO) {Log.d(TAGe,"loginUser ViewModelScope")
            task}.invokeOnCompletion {viewModelScope.launch{}}
        return liveData {try {task.await()
                if (task.isSuccessful){emit(true)}else{emit(false)}}catch (e: FirebaseAuthInvalidUserException){emit(false)}catch (e : Exception){Log.e(TAGe, "loginUser: $e ", e)}}}

fun signUp(email: String,password: String):LiveData<Boolean>{
    try {

    }catch (c: FirebaseAuthInvalidCredentialsException){
        Log.d(TAG, "signUp: $c")
    }
    val task=repo.signUp(email, password)
    viewModelScope.launch (Dispatchers.IO){
        task
    }.invokeOnCompletion { viewModelScope.launch {} }
    return liveData {
        try {
            task.await()
            if (task.isSuccessful){
                emit(true)
            }else{
                emit(false)
            }

        }catch (e:FirebaseFirestoreException){

        }

    }
}
}





