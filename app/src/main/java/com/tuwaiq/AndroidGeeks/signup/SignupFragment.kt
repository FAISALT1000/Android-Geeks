package com.tuwaiq.AndroidGeeks.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.AndroidGeeks.MainActivity
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.Post.POST_ID
import com.tuwaiq.AndroidGeeks.Post.POST_TEXT
import com.tuwaiq.AndroidGeeks.login.LoginFragment
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import com.tuwaiq.AndroidGeeks.databinding.PostFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.SignupFragmentBinding
import com.tuwaiq.AndroidGeeks.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.coroutines.tasks.await
import java.util.*
const val USERNAME_ID="username"
const val EMAIL_ID="email"
private const val TAG = "SignupFragment"
class SignupFragment : Fragment() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
    private lateinit var iDontLikeJava:CheckBox
    private lateinit var usernameEt:EditText
    private  var dataBase= FirebaseFirestore.getInstance()
    private lateinit var binding: SignupFragmentBinding
    private lateinit var auth: FirebaseAuth
    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[SignupViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SignupFragmentBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()

        val view= inflater.inflate(R.layout.signup_fragment, container, false)
        val alreadyExists:TextView=view.findViewById(R.id.already_user)

        usernameEt=view.findViewById(R.id.username_et)
        emailEt=view.findViewById(R.id.email_et)
        passwordEt=view.findViewById(R.id.password_et)
        iDontLikeJava=view.findViewById(R.id.terms_conditions)
        signupBtn=view.findViewById(R.id.signUpBtn)
        loginLink=view.findViewById(R.id.login_tv)





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
            try {
            registerUser()
            }catch (e: FirebaseAuthInvalidCredentialsException){
                Snackbar.make(requireView(),"FirebaseAuthInvalidCredentialsException",Snackbar.LENGTH_SHORT).show()
            }


        }
        already_user.setOnClickListener {
            val fragment= LoginFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                ?.addToBackStack(null)?.commit()
        }

    }/**/
    private fun registerUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()


        if (email.isNotEmpty()&& pass.isNotEmpty()){
            if(iDontLikeJava.isChecked){
                    auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                val intent=Intent(context,MainActivityForTesting::class.java)
                                startActivity(intent)

                                val userName=usernameEt.text.toString().trim()
                                val email=emailEt.text.toString().trim()
                                val userIdd= FirebaseAuth.getInstance().currentUser?.uid.toString()

                                val userInfo= UsersInfo(userIdd,userName,email)
                                updateUserInfo(userIdd,userInfo)


                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                context,
                                "FailureListener${it.message}",
                                Toast.LENGTH_LONG
                            ).show()

                        }}else{
                            Snackbar.make(requireView(),getString(R.string.you_must_accept_the_terms),Snackbar.LENGTH_LONG).show()

                        }

    }else{
        Snackbar.make(requireView(), getString(R.string.please_fill_all_the_filed), Snackbar.LENGTH_LONG).show()
    }}

    fun updateUserInfo(userId:String,userInfo:UsersInfo){
        dataBase = FirebaseFirestore.getInstance()
            if (userId != null) {
                dataBase.collection("users").document(userId)
                    .set(userInfo).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(context,getString(R.string.success_toast),Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivityForTesting::class.java)
                startActivity(intent)}else{}}
                    .addOnFailureListener {Toast.makeText(context,getString(R.string.failure_toast),Toast.LENGTH_SHORT).show() }}}

}

