package com.tuwaiq.AndroidGeeks.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.AndroidGeeks.MainActivity
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.signup.SignupFragment
import com.tuwaiq.AndroidGeeks.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_login.*

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var emailEt:EditText
    private lateinit var passwordEt:EditText
    private lateinit var loginBtn:Button
    private lateinit var progressBar: ProgressBar
    private lateinit var signup:TextView
    private  var auth= FirebaseAuth.getInstance()


    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[LoginViewModel::class.java] }

    val userId= auth.currentUser?.uid






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: waaw")
//        if (userId!=null){
//
//            val intent= Intent(context, MainActivity::class.java)
//            startActivity(intent)
//        }


        val view= inflater.inflate(R.layout.fragment_login, container, false)


        progressBar=view.findViewById(R.id.login_progressBar)
        emailEt=view.findViewById(R.id.emailet_login)
         passwordEt=view.findViewById(R.id.passwordet_login)
         loginBtn  =view.findViewById(R.id.login_btn)
        signup=view.findViewById(R.id.sign_up_tv)
        progressBar.visibility=View.GONE
        val fingerPrint:ImageView=view.findViewById(R.id.finger_print)




        return view
    }

    override fun onStart() {
        super.onStart()
        val userId= auth.currentUser?.uid
        if (userId==null){ finger_print.visibility=View.GONE}

        finger_print.setOnClickListener {
        val intent=Intent(context,MainActivityForTesting::class.java)
            startActivity(intent)
        }


        signup.setOnClickListener {

            val fragment= SignupFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                ?.addToBackStack(null)?.commit()

        }
        loginBtn.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            loginUser() }
        progressBar.visibility=View.GONE



    }
    private fun loginUser(){

        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){
            progressBar.visibility=View.VISIBLE

            fragmentViewModel.loginUser(email,pass).observe(this){
                if (it){ //
                    Snackbar.make(requireView(), getString(R.string.login_successfully_toast), Snackbar.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Snackbar.make(requireView(), getString(R.string.login_not_successfully_toast), Snackbar.LENGTH_LONG).show()
                progressBar.visibility=View.GONE}}
        }}






}


