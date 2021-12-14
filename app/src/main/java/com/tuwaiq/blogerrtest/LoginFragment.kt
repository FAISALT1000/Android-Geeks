package com.tuwaiq.androidgeeks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuwaiq.blogerrtest.MainActivity
import com.tuwaiq.blogerrtest.R
import com.tuwaiq.blogerrtest.ui.dashboard.DashboardFragment


class LoginFragment : Fragment() {

    private lateinit var emailEt:EditText
    private lateinit var passwordEt:EditText
    private lateinit var loginBtn:Button
    private lateinit var googleBtn:ImageView
    private lateinit var navBottomNavigation:BottomNavigationMenuView
   // private lateinit var navHost:NavHostFragment



    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_login, container, false)


        emailEt=view.findViewById(R.id.emailet_login)
         passwordEt=view.findViewById(R.id.passwordet_login)
         loginBtn  =view.findViewById(R.id.login_btn)
        googleBtn  =view.findViewById(R.id.google_btn)
       // navBottomNavigation=view.findViewById(R.id.nav_bottomview)



       // navBottomNavigation.visibility=View.VISIBLE




       loginBtn.setOnClickListener {

               loginUser()
       }

        googleBtn.setOnClickListener {
            Toast.makeText(context,"R.string.soon",Toast.LENGTH_LONG).show()
        }

        return view
    }
    private fun loginUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener{ task->
                    if (task.isSuccessful){
                        val firebaseUser: FirebaseUser =task.result!!.user!!
                        Toast.makeText(context,"logged in", Toast.LENGTH_LONG).show()

                        val intent= Intent(context, MainActivity::class.java)
                        startActivity(intent)
//                        val fragment= DashboardFragment()
//                        activity?.supportFragmentManager
//                            ?.beginTransaction()?.replace(R.id.nav_host_fragment_activity_main,fragment)
//                            ?.addToBackStack(null)?.commit()
//                        val intent=Intent(context, MainActivity2::class.java)
//                        intent.putExtra("user_id",firebaseUser.uid)
//                        intent.putExtra("email_id",firebaseUser.email)
//                        startActivity(intent)
//                        finish()
                    }
                    else{
                        Toast.makeText(context,"Try Again", Toast.LENGTH_LONG).show()
//                        val fragment=LoginFragment()
//                        activity?.supportFragmentManager
//                            ?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)
//                            ?.addToBackStack(null)?.commit()


                    }
                }
        }}
    }


