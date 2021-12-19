package com.tuwaiq.blogerrtest.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel:ViewModel() {
    private lateinit var database:FirebaseFirestore
    private lateinit var auth: FirebaseAuth


   fun loginUser(){
       val email:String=""
       val pass:String=""
       FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass)
   }
}



