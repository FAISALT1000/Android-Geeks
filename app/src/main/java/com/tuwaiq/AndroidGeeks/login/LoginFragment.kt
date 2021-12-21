package com.tuwaiq.AndroidGeeks.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.AndroidGeeks.signup.SignupFragment
import com.tuwaiq.AndroidGeeks.MainActivity
import com.tuwaiq.AndroidGeeks.R


class LoginFragment : Fragment() {

    private lateinit var emailEt:EditText
    private lateinit var passwordEt:EditText
    private lateinit var loginBtn:Button
    private lateinit var googleBtn:ImageView
    private lateinit var signUpBtn:TextView
    private lateinit var progressBar: ProgressBar
    private  var auth= FirebaseAuth.getInstance()

    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[LoginViewModel::class.java] }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth.signOut()

        val view= inflater.inflate(R.layout.fragment_login, container, false)


        progressBar=view.findViewById(R.id.login_progressBar)
        emailEt=view.findViewById(R.id.emailet_login)
         passwordEt=view.findViewById(R.id.passwordet_login)
         loginBtn  =view.findViewById(R.id.login_btn)
        googleBtn  =view.findViewById(R.id.google_btn)
        signUpBtn  =view.findViewById(R.id.singup_tv_loginpage)
        progressBar.visibility=View.GONE

        signUpBtn.setOnClickListener {

            val fragment= SignupFragment()
                        activity?.supportFragmentManager
                            ?.beginTransaction()?.replace(R.id.nav_host_fragment_activity_main,fragment)
                            ?.addToBackStack(null)?.commit() }

       loginBtn.setOnClickListener {
           progressBar.visibility=View.VISIBLE
               loginUser() }
        googleBtn.setOnClickListener {
            Toast.makeText(context,"R.string.soon",Toast.LENGTH_LONG).show() }
        return view
    }

    override fun onStart() {
        super.onStart()

        signUpBtn.setOnClickListener {

            val fragment= SignupFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.nav_host_fragment_activity_main,fragment)
                ?.addToBackStack(null)?.commit() }

        loginBtn.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            loginUser() }
        googleBtn.setOnClickListener {
            Toast.makeText(context,R.string.soon,Toast.LENGTH_LONG).show() }


    }
    private fun loginUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        var isSuccessful=true//:Boolean=fragmentViewModel.loginUser(null,null,false)
        if (email.isNotEmpty()&& pass.isNotEmpty()){

            fragmentViewModel.loginUser(email,pass,isSuccessful)
      //      if (isSuccessful=true){
//                .addOnCompleteListener{ task->
//                    if (task.isSuccessful){
                        Toast.makeText(context,"logged in", Toast.LENGTH_LONG).show()
//
                       val intent= Intent(context, MainActivity::class.java)
                        startActivity(intent)
//                   }
//                    else{
//                        Toast.makeText(context,"Try Again", Toast.LENGTH_LONG).show()
//                    }
//                }
        }else{ val fragment=SignupFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                ?.addToBackStack(null)?.commit()


        }}
    }

