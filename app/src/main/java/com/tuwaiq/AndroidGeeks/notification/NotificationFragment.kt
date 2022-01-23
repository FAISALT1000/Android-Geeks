package com.tuwaiq.AndroidGeeks.notification

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.api.data.remote.ApiServiceImpl
import com.tuwaiq.AndroidGeeks.databinding.CommentFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.NotificationFragmentBinding
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val NOTIFICATION_FIREBASE="/AndroidGeeks/myNotification"
private const val TAG = "NotificationFragment"
class NotificationFragment : Fragment() {
    private val client= HttpClient(Android)
    private val service=ApiServiceImpl(client)

   private lateinit var binding: NotificationFragmentBinding
    private lateinit var viewModel: NotificationViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding= NotificationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().subscribeToTopic(NOTIFICATION_FIREBASE)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener {
            FirebaseFirestore.getInstance().collection("Posts").document().delete().addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Done Posts", Toast.LENGTH_SHORT).show()
                }
            }
        binding.button3.setOnClickListener {
            FirebaseFirestore.getInstance().collection("Comments").document().delete().addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            }
            }

        }}
        FirebaseFirestore.getInstance().collection("Posts").get().addOnSuccessListener {
                binding.postNumberEt.text =it.documents.size.toString()
        }

        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener {
            binding.userNumberEt.text =it.documents.size.toString()
        }

        FirebaseFirestore.getInstance().collection("Comments").get().addOnSuccessListener {
            binding.commentNumberEy.text =it.documents.size.toString()
        }
        binding.postNumberEt.text
        binding.sendNotBtn.setOnClickListener {
            Log.d(TAG, "onCreate: sendNotBtn")
            //ktor
            /* scope.launch(Dispatchers.IO) {

                 service.sendNotification(title, description)
             }*/
            val title = binding.titleEt.text.toString()
            val message = binding.decNotEt.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() ) {
                Log.d(TAG, "onCreate: sendNotBtn  if(title.isNotEmpty() && message.isNotEmpty() ) {")

                PushNotification(
                    NotificationData(title, message),
                    NOTIFICATION_FIREBASE
                ).also {
                    Log.d(TAG, "onCreate: also")
                    sendNotification(it)
                }
            }

        }
    }
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            Log.d(TAG, "sendNotification: ")
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }


}