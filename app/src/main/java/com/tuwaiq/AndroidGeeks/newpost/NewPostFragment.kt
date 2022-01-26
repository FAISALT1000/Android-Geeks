package com.tuwaiq.AndroidGeeks.newpost


import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.criminalintent.utils.getScaledBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.tuwaiq.AndroidGeeks.MainActivityForTesting
import com.tuwaiq.AndroidGeeks.R
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.databinding.NewPostFragmentBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "NewPostFragment"
private const val REQUEST_CONTACT=1
class NewPostFragment : Fragment() {
    private lateinit var binding: NewPostFragmentBinding
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var photoFile: File
    private var photoUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var viewModel: NewPostViewModel
    private var posts = Posts()
    private val getResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        photoUri = it
        binding.postImageView.setImageURI(it)
    }
    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}


    private val fragmentViewModel by lazy { ViewModelProvider(this)[NewPostViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (userId == null) {
            val intent = Intent(context, MainActivityForTesting::class.java)
            startActivity(intent)
            Toast.makeText(context, getString(R.string.you_must_sign_toast), Toast.LENGTH_SHORT)
                .show()
        }
        dataBase = FirebaseFirestore.getInstance()
        dataBase.collection("Post Image").get()
        binding = NewPostFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.data != null) {
            photoUri = data?.data!!

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context, getString(R.string.new_post), Toast.LENGTH_SHORT).show()
        super.onViewCreated(view, savedInstanceState)

        binding.postBtn.setOnClickListener {
            addPost()
        }
        binding.uploadPostBtn.setOnClickListener {
            //    selectImage()
            getResult.launch("image/*")

        }


    }


    private fun addPost() {
        val title = binding.titleTv.text.toString()
        val description = binding.postEt.text.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val todayDate = Date()
        val fileName = formatter.format(todayDate)
        val storage = FirebaseStorage.getInstance()
        val fileName2 = "image/$title/$userId/$fileName"
        val storagee = storage.getReference(fileName2)
        val date = Date()
        if (title.isNotEmpty() && description.isNotEmpty()) {


            if (photoUri != null) {
                var post = fragmentViewModel.addPost(
                    userID = userId,
                    title = title,
                    description = description,
                    date = date,
                    photoUri!!
                )
            }
            Toast.makeText(context, getString(R.string.add_post_successful), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, getString(R.string.fill_the_field), Toast.LENGTH_SHORT).show()
        }
    }
}