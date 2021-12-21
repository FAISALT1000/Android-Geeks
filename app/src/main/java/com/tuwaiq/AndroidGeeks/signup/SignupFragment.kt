package com.tuwaiq.AndroidGeeks.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuwaiq.AndroidGeeks.login.LoginFragment
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.login.LoginViewModel

private const val TAG = "SignupFragment"
class SignupFragment : Fragment() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[SignupViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.signup_fragment, container, false)


        emailEt=view.findViewById(R.id.email_et)
        passwordEt=view.findViewById(R.id.password_et)
        signupBtn=view.findViewById(R.id.signin_btn)
        loginLink=view.findViewById(R.id.login_tv)
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

    }
    private fun registerUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){
            fragmentViewModel.newUser(email,pass)
            Log.d(TAG," registerUser()")

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
//
//
//
//                }
    //    }
    }else{
            Toast.makeText(context,"plz fil all the fields", Toast.LENGTH_LONG).show()



        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

    }

}