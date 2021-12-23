package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.R
import java.util.*

private const val TAG = "DashboardFragment"
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var emailTv:EditText
    private lateinit var firstNameEt:EditText
    private lateinit var lastNameEt:EditText
    private lateinit var usernameEt:EditText
    private lateinit var phoneNumberEt:EditText
    private lateinit var textView: EditText
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var saveBtn:Button
    private lateinit var logoutBtn:Button
    private lateinit var updateDate:Date
    private lateinit var auth: FirebaseAuth
   // private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
   // private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)/*
//        dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

//        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//         textView= binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
         */
        logoutBtn=view.findViewById(R.id.logoutbtn)
        saveBtn=view.findViewById(R.id.save_btn)
        usernameEt=view.findViewById(R.id.username_et)
        emailTv=view.findViewById(R.id.email_tv)
        textView=view.findViewById(R.id.uid_tv)
        firstNameEt=view.findViewById(R.id.firstname_et)
        lastNameEt=view.findViewById(R.id.lastname_et)
        phoneNumberEt=view.findViewById(R.id.phonenamber_et)
        updateDate=Date()


        getUserInfo()

        val userEmail= FirebaseAuth.getInstance().currentUser?.email
        userEmail.toString()
        emailTv.setText(userEmail)

        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId.toString()
        textView.setText(userId)

        saveBtn.setOnClickListener {
            updateUserInfo()
        }



        logoutBtn.setOnClickListener {
            Log.d(TAG,"Logout")
            Toast.makeText(context,"Logout successful", Toast.LENGTH_LONG).show()
            val intent=Intent(context,MainActivityForTesting::class.java)
            startActivity(intent)



        }
        return view
    }
    fun getUserInfo()
    {
        dataBase = FirebaseFirestore.getInstance()
        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId.toString()
        if (userId != null) {
            val fireStoreData = dataBase.collection("users").document(userId).get() }


        if (userId != null) {
            dataBase.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    print(document.data)
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)}}

    }
    fun updateUserInfo(/*titleEt:Object,postEt:Object,date: Date*/){
        dataBase = FirebaseFirestore.getInstance()

        val userName=usernameEt.text.toString().trim()
        val firstName=firstNameEt.text.toString().trim()
        val lastName=lastNameEt.text.toString().trim()
        val phoneNumber=phoneNumberEt.text.toString().trim()
        val updateDate=updateDate

        //  val userId= auth.currentUser?.let { it.email }
        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId.toString()

        var userInfo= userId?.let { DashboardViewModel(userId,userName,firstName,lastName,phoneNumber,updateDate) }
        if (userInfo != null) {
            if (userId != null) {
                dataBase.collection("users").document(userId)
                    .set(userInfo)
        //                .add(userInfo)
                    .addOnSuccessListener {
                        Toast.makeText(context,getString(R.string.success_toast),Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,getString(R.string.failure_toast),Toast.LENGTH_SHORT).show() }
            }
        }
    }


}