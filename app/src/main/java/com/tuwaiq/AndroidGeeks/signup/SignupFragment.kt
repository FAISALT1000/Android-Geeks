package com.tuwaiq.AndroidGeeks.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.login.LoginFragment
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import com.tuwaiq.AndroidGeeks.databinding.PostFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.SignupFragmentBinding
import com.tuwaiq.AndroidGeeks.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.signup_fragment.*
import java.util.*

private const val TAG = "SignupFragment"
class SignupFragment : Fragment() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
    private lateinit var firstNameEt:EditText
    private lateinit var lastNameEt:EditText
    private lateinit var confirmPasswordEt:EditText
    private lateinit var confirmEmailEt:EditText
    private lateinit var gender:EditText
    private lateinit var iDontLikeJava:CheckBox
    private lateinit var usernameEt:EditText
    private lateinit var phoneNumberEt:EditText
    private  var dataBase= FirebaseFirestore.getInstance()
    private lateinit var binding: SignupFragmentBinding
    private lateinit var logoutBtn:Button
    private lateinit var auth: FirebaseAuth
    private var userInfo= UsersInfo()
    private val userID= FirebaseAuth.getInstance().currentUser?.uid
    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[SignupViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SignupFragmentBinding.inflate(layoutInflater)

        val view= inflater.inflate(R.layout.signup_fragment, container, false)

        firstNameEt=view.findViewById(R.id.firstname_et)
        lastNameEt=view.findViewById(R.id.lastname_et)
        usernameEt=view.findViewById(R.id.username_et)
        emailEt=view.findViewById(R.id.email_et)
        gender=view.findViewById(R.id.gendr_et)
        phoneNumberEt=view.findViewById(R.id.phonenumber_et)
        passwordEt=view.findViewById(R.id.password_et)
        confirmPasswordEt=view.findViewById(R.id.confirmPassword)
        iDontLikeJava=view.findViewById(R.id.terms_conditions)
        confirmEmailEt=view.findViewById(R.id.confirmemail_et)
        signupBtn=view.findViewById(R.id.signUpBtn)
        loginLink=view.findViewById(R.id.login_tv)
//[]
//        con=view.findViewById(R.id.cons)

       // con.visibility=View.VISIBLE





        return view
    }

    override fun onStart() {
        super.onStart()
        loginLink.setOnClickListener {
            val fragment= LoginFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                ?.addToBackStack(null)?.commit()

        }

        signupBtn.setOnClickListener {
            registerUser()


        }

    }//kk mm nn jkj ll
    private fun registerUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){
            fragmentViewModel.newUser(email,pass)
           val userId= FirebaseAuth.getInstance().currentUser?.uid
            Log.d(TAG," registerUser()")
/*
//                .addOnCompleteListener{ task->
//                    if (task.isSuccessful){
                        Toast.makeText(context,"logged in", Toast.LENGTH_LONG).show()
//                        val fragment= LoginFragment()
//                        activity?.supportFragmentManager
//                            ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
//                            ?.addToBackStack(null)?.commit()
//                   }
//                    else{
//                        Toast.makeText(context,"Try Again", Toast.LENGTH_LONG).show()
//                }
    //    }

 */
            if (userId != null) {
                //there a problem here, we need to click sign up button two time to add user info
                    //maybe we need a del this fun for second
                updateUserInfo(userId)
            }
        }else{
            Toast.makeText(context,"plz fil all the fields", Toast.LENGTH_LONG).show()

        }
    }
    fun updateUserInfo(userId:String){
        dataBase = FirebaseFirestore.getInstance()
        val userName=usernameEt.text.toString().trim()
        val firstName=firstNameEt.text.toString().trim()
        val lastName=lastNameEt.text.toString().trim()
        val profileImage:String=""
        val phoneNumber=phoneNumberEt.text.toString().trim()
        val userIdd= FirebaseAuth.getInstance().currentUser?.uid.toString()
        val gender= gender.text.toString().trim()
        var userInfo= UsersInfo(userIdd,userName,firstName,lastName,phoneNumber,profileImage,gender)

        //  val userId= auth.currentUser?.let { it.email }

        userId.toString()


        if (userInfo != null) {
            if (userId != null) {
                dataBase.collection("users").document(userId)
                    .set(userInfo)

                    //                .add(userInfo)
                    .addOnSuccessListener {Toast.makeText(context,getString(R.string.success_toast),Toast.LENGTH_SHORT).show()}
                    .addOnFailureListener {Toast.makeText(context,getString(R.string.failure_toast),Toast.LENGTH_SHORT).show() }}}}

}