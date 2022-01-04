package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

private const val TAG = "DashboardFragment"
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var emailTv:TextView
    private lateinit var firstNameEt:TextView
    private lateinit var lastNameEt:TextView
    private lateinit var usernameEt:TextView
    private lateinit var phoneNumberEt:TextView
    private lateinit var textView: TextView
    private lateinit var dataBase: FirebaseFirestore

    private lateinit var saveBtn:Button
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var logoutBtn:Button
    private lateinit var updateDate:Date
    private lateinit var auth: FirebaseAuth
    private var post=Posts()
    private val userID= FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)

        //logoutBtn=view.findViewById(R.id.logoutbtn)
       // saveBtn=view.findViewById(R.id.save_btn)
        usernameEt=view.findViewById(R.id.username_et)
        emailTv=view.findViewById(R.id.email_tv)
        textView=view.findViewById(R.id.uid_tv)
        firstNameEt=view.findViewById(R.id.firstname_et)
        //lastNameEt=view.findViewById(R.id.lastname_et)
        phoneNumberEt=view.findViewById(R.id.phonenamber_et)
        updateDate=Date()

        if (userID != null) {
            getUserInfo1(userID)
        }








        /*

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



        getUserInfo()
        getUserInfo()

        val userEmail= FirebaseAuth.getInstance().currentUser?.email
        userEmail.toString()
        emailTv.setText(userEmail)

        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId.toString()
        textView.setText(userId)

       // val userName= userId?.let { dataBase.document(it).get() }

//        saveBtn.setOnClickListener {
//            updateUserInfo()
//            if (userID != null) {
//                getUserInfo1(userID)
//            }
//        }



//        logoutBtn.setOnClickListener {
//            auth.signOut()
//            Log.d(TAG,"Logout")
//            Toast.makeText(context,"Logout successful", Toast.LENGTH_LONG).show()
//            val intent=Intent(context,MainActivityForTesting::class.java)
//            startActivity(intent) }
        return view
    }


    fun getUserInfo1(userID: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("users")
                .document("${userID}")
                .get().addOnCompleteListener {
                    it
                    if (it.result?.exists()!!) {
                        var userName = it.result!!.getString("userName")
                        var firstName = it.result!!.getString("firstName")
                        var lastName = it.result!!.getString("lastName")
                        var phoneNumber = it.result!!.getString("phoneNumber")
//                        var userEmail = it.result!!.getString("userEmail")
//                        var userFollowing = it.result!!.get("following")
//                        var userFollowers = it.result!!.get("followers")
//                        var userPhone = it.result!!.getString("userPhone")//moreInfo
//                        var userInfo = it.result!!.getString("moreInfo")//moreInfo
                      //  Log.e("user Info", "userName ${name.toString()} \n ${userEmail.toString()}")
                        usernameEt.setText(userName)
                        firstNameEt.setText(firstName)
                    //    lastNameEt.setText(lastName)//**//**
                        phoneNumberEt.setText(phoneNumber)
                        //---------
                        //usernameEt.setText(userName)
//                            binding.userFollowersXml.text = "${userFollowers?.toString()}"
//                            binding.userFollowingXml.text = "${userFollowing?.toString()}"
                        //   userPhoneNumber = "${userPhone.toString()}"
                    } else {
                        Log.e("error \n", "errooooooorr")
                    }
                }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // Toast.makeText(coroutineContext,0,0, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")
            }
        }
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
//                    print(document.data)
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
                    .addOnSuccessListener {Toast.makeText(context,getString(R.string.success_toast),Toast.LENGTH_SHORT).show()}
                    .addOnFailureListener {Toast.makeText(context,getString(R.string.failure_toast),Toast.LENGTH_SHORT).show() }}}}


}