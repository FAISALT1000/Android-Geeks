package com.tuwaiq.AndroidGeeks.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.Post.POST_ID
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.database.Users.UsersInfo
import com.tuwaiq.AndroidGeeks.databinding.FragmentDashboardBinding
import com.tuwaiq.AndroidGeeks.signup.EMAIL_ID
import com.tuwaiq.AndroidGeeks.signup.SignupViewModel
import com.tuwaiq.AndroidGeeks.signup.USERNAME_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


private const val TAG2 = "DF"
private const val TAG = "DashboardFragment"
private const val TAG34 = "DashboardFragment111"
class DashboardFragment : Fragment() {
    private lateinit var firstNameEt:TextView
    private lateinit var lastNameEt:TextView
    private lateinit var usernameEt:TextView
    private lateinit var phoneNumberEt:Button
    private lateinit var profileImageView: ImageView
    private lateinit var postNum:TextView
    private  var dataBase=FirebaseFirestore.getInstance()
    private lateinit var signOutBtn:Button
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var no_btn:Button
    private lateinit var updateDate:Date
    private var profilePhotoUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private var userInfo=UsersInfo()
    private var post=Posts()
    private val darkModeSwitch get() = context?.let { UserPreference.loadNightModeState(it) }
    private  val fragmentViewModel by lazy{ ViewModelProvider(this)[DashboardViewModel::class.java] }


    private val userID= FirebaseAuth.getInstance().currentUser?.uid
    val getResult= registerForActivityResult(ActivityResultContracts.GetContent()){ profilePhotoUri=it
        profileImageView.setImageURI(it)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(layoutInflater)
        // userSettings=UserPreference().getSharedPreferences()
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)


        profileImageView=view.findViewById(R.id.profile_image_view)

        updateDate=Date()


        if (userID != null) {
            dataBase.collection("Posts").whereEqualTo("userId",userID).get().addOnSuccessListener {
                binding.postNumEt.text=it.documents.size.toString()
            }

            dataBase.collection("users").document(userID).collection("Favorite").whereEqualTo("userId","$userID")
                .get().addOnSuccessListener {
                binding.LikeNumEt.text=it.documents.size.toString()

            }
            getUserInfo1(userID)
        }
        if (darkModeSwitch ==true){
            binding.switch1.isChecked = true
            UserPreference.setNightModeState(requireContext(),true)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        }else{
            binding.switch1.isChecked = false
            UserPreference.setNightModeState(requireContext(),false)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

        }
        binding.switch1.setOnClickListener {

            if (binding.switch1.isChecked){
                Log.d("switch1", "onCreateView:isChecked ")
                UserPreference.setNightModeState(requireContext(),true)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }else{
                Log.d("switch1", "onCreateView:isUnChecked ")

                UserPreference.setNightModeState(requireContext(),false)}


        }
        getUserInfo()
        getUserInfo()
        //
        val userEmail= FirebaseAuth.getInstance().currentUser?.email
        userEmail.toString()
        //emailTv.setText(userEmail)

        val userId= FirebaseAuth.getInstance().currentUser?.uid
        userId.toString()

        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode== Activity.RESULT_OK && data!=null && data.data!=null){
            profilePhotoUri = data?.data!!

        }
    }

    override fun onStart() {
        super.onStart()
binding.noBtn.setOnClickListener {
    Firebase.auth.currentUser?.delete()?.addOnCompleteListener {
        if (it.isSuccessful){
            Toast.makeText(context, getString(R.string.delete_user_toast_successful), Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener {
                Log.d(TAG, "onStart: ")
            }
        }else{
            Toast.makeText(context, getString(R.string.delete_user_toast_un_successful), Toast.LENGTH_SHORT).show()

        }
    }
}

        binding.signoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Log.d(TAG, "onStart: sign out")
            //-+k
            val intent = Intent(context, MainActivityForTesting::class.java)
            startActivity(intent)
            Toast.makeText(context, getString(R.string.sign_out_toast), Toast.LENGTH_SHORT).show()
        }
        profileImageView.setOnClickListener {

            getResult.launch("image/*")
            dataBase.collection("users")
                .document("${userID}")
                .get().addOnCompleteListener { it
                    if (it.result?.exists()!!){
                        var userName = it.result!!.getString("userName")
                        if (userName!=null ){
                            uploadImage(userName, profilePhotoUri!!)}

                    }}

        }
        binding.postNumEt.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            db.collection("users")
                .document("${userID}").update("phoneNumber","051154125")
            Snackbar.make(requireView(), "This is main activity "+"$it", Snackbar.LENGTH_LONG).show()

        }

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

                        binding.usernameEt.text = userName
//                        binding.phonenumberrEt.setText()
                        //   firstNameEt.setText(firstName)
                        //    lastNameEt.setText(lastName)//**//**
                        // phoneNumberEt.setText(phoneNumber)
                        //---------
                        //usernameEt.setText(userName)
//                            binding.userFollowersXml.text = "${userFollowers?.toString()}"
//                            binding.userFollowingXml.text = "${userFollowing?.toString()}"
                        //   userPhoneNumber = "${userPhone.toString()}"
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
//                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")


                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)}}

    }


    private fun uploadImage(userName:String,photoUri: Uri) {
//        val progressDialog= ProgressDialog(context)
//        progressDialog.setMessage("Uploading File.....")
//        progressDialog.setCancelable(false)
//        progressDialog.show()

        val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val todayDate=Date()
        val fileName=formatter.format(todayDate)
        val  storage= FirebaseStorage.getInstance()

        val storagee=storage.getReference("profiles/image/$userName/$fileName")
        val uploadtask= storagee.putFile(photoUri!!)
        uploadtask.continueWithTask{task->
            if (!task.isSuccessful){
                task.exception?.let { throw it }
            }
            storagee.downloadUrl
        }.addOnCompleteListener { task->
            if (task.isSuccessful){
                task.result
                if (userID!=null){
                    val ref = dataBase.collection("users").document(userID)

                    ref
                        .update("profileImageUrl", task.result.toString())
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
                }}
        }
        /*.addOnSuccessListener{ binding.postImageView.setImageURI(null)

        Toast.makeText(context,"Successfully Upload The Image",Toast.LENGTH_SHORT).show()

            if (progressDialog.isShowing) progressDialog.dismiss()
        }
        .addOnFailureListener{
            if (progressDialog.isShowing)progressDialog.dismiss()
            Toast.makeText(context,"Field",Toast.LENGTH_SHORT).show()
        }*/
        /*






        */

    }


}