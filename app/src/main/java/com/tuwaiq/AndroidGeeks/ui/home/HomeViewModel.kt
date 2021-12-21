package com.tuwaiq.AndroidGeeks.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.database.BlogRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private lateinit var database:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val repo=BlogRepo()

    fun getAllPost()/*: Task<DocumentSnapshot>*/ {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllPost()
        }.invokeOnCompletion {viewModelScope.launch{}}
    }
}