package com.tuwaiq.androidgeeks.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuwaiq.blogerrtest.login.LoginFragment
import com.tuwaiq.blogerrtest.R

class SignupFragment : Fragment() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
    private lateinit var fragment: FragmentContainerView
  // private lateinit var con:LinearLayout
    //private lateinit var viewModel:SignupViewModel

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

        loginLink.setOnClickListener {
            val fragment= LoginFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                ?.addToBackStack(null)?.commit()

        }

        signupBtn.setOnClickListener {
            registerUser()

        }



        return view
    }
    private fun registerUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener{ task->
                    if (task.isSuccessful){
                        val firebaseUser: FirebaseUser=task.result!!.user!!
                        Toast.makeText(context,"logged in", Toast.LENGTH_LONG).show()
                        val fragment= LoginFragment()
                        activity?.supportFragmentManager
                            ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
                            ?.addToBackStack(null)?.commit()
////                        val intent=Intent(context, MainActivity2::class.java)
//////                        intent.putExtra("user_id",firebaseUser.uid)
//////                        intent.putExtra("email_id",firebaseUser.email)
////                        startActivity(intent)
////                       // finish()
                   }
                    else{
                        Toast.makeText(context,"Try Again", Toast.LENGTH_LONG).show()



                }
        }
    }else{
            Toast.makeText(context,"plz fil all the fields", Toast.LENGTH_LONG).show()



        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

    }

}