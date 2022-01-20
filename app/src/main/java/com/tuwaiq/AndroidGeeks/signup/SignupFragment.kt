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
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.Post.POST_ID
import com.tuwaiq.AndroidGeeks.login.LoginFragment
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import com.tuwaiq.AndroidGeeks.databinding.PostFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.SignupFragmentBinding
import com.tuwaiq.AndroidGeeks.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.signup_fragment.*
import java.util.*
const val USERNAME_ID="username"
const val EMAIL_ID="email"
private const val TAG = "SignupFragment"
class SignupFragment : Fragment() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
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
        auth= FirebaseAuth.getInstance()

        val view= inflater.inflate(R.layout.signup_fragment, container, false)

        usernameEt=view.findViewById(R.id.username_et)
        emailEt=view.findViewById(R.id.email_et)
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
            try {
            registerUser()
            }catch (e: FirebaseAuthInvalidCredentialsException){
                Snackbar.make(requireView(),"FirebaseAuthInvalidCredentialsException",Snackbar.LENGTH_SHORT).show()
            }


        }

    }/**/
    private fun registerUser(){
        val email= emailEt.text.toString()
        val pass=passwordEt.text.toString()
        if (email.isNotEmpty()&& pass.isNotEmpty()){

          /*  fragmentViewModel.signUp(email,pass).observe(this){

               if (it) {
                   Toast.makeText(context, "true", Toast.LENGTH_SHORT).show()

                   val intent = Intent(context, MainActivityForTesting::class.java)
                   startActivity(intent)


                   Snackbar.make(requireView(), getString(R.string.success_toast), Snackbar.LENGTH_LONG).show()
               }else{

             Snackbar.make(requireView(), getString(R.string.failure_toast), Snackbar.LENGTH_LONG).show()
               }
                //updateUserInfo()

         }*/
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    updateUserInfo()
                    val intent = Intent(context, MainActivityForTesting::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
                Toast.makeText(context, "FailureListener${it.message}", Toast.LENGTH_LONG).show()
            }


    }else{
        Snackbar.make(requireView(), "plz fill all the filed", Snackbar.LENGTH_LONG).show()
    }
    }
    fun updateUserInfo(){




        fragmentViewModel.addUserInfo(userInfo,userID!!).observe(this){
            Log.d(TAG, "updateUserInfo: $userID")
            if (it){
                Snackbar.make(requireView(), getString(R.string.success_toast), Snackbar.LENGTH_LONG).show()
                Log.d(TAG, "updateUserInfo: success")
            }else{
                Log.d(TAG, "updateUserInfo: failure")
                Snackbar.make(requireView(), getString(R.string.failure_toast), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}