package com.tuwaiq.AndroidGeeks.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

private const val TAG = "LoginViewModel"
private const val TAGe = "Login ERROR"

class LoginViewModel:ViewModel() {
    private lateinit var database:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var repo:BlogRepo = BlogRepo()


   fun loginUser(email:String,password:String):LiveData<Boolean>{
       val task =  repo.loginUser(email,password)
       viewModelScope.launch(Dispatchers.IO) {
           Log.d(TAG,"loginUser ViewModelScope")
           task
       }.invokeOnCompletion {viewModelScope.launch{}}
       return liveData {


          try {
              task.await()
              if (task.isSuccessful){
                  emit(true)
              }else{
                  emit(false)
              }
          }catch (e: FirebaseAuthInvalidUserException){
              emit(false)}catch (e : Exception){Log.e(TAGe, "loginUser: $e ", e)}}}}



