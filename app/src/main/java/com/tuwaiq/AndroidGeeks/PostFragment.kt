package com.tuwaiq.AndroidGeeks


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.databinding.NewPostFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.PostFragmentBinding
import java.util.*

private const val TAG = "PostFragment"
class PostFragment : BottomSheetDialogFragment() {
    private val args by navArgs<PostFragmentArgs>()
    private  var posts = Posts()
    private lateinit var binding: PostFragmentBinding
    private lateinit var dp:FirebaseStorage
    private lateinit var database:FirebaseFirestore
    private lateinit var storage:StorageReference
    private lateinit var deleteBtn:Button
    private lateinit var viewModel: PostViewModel
    private val userId= FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // addCommit()
        dp= FirebaseStorage.getInstance()
        database= FirebaseFirestore.getInstance()
        storage=dp.getReference()
        binding= PostFragmentBinding.inflate(layoutInflater)

        return binding.root
    }
    /****//****/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id=args.dataPost.id
        val title=args.dataPost.title


        binding.deleteBtn.setOnClickListener {
            de(title, id)
            database.collection("Posts").document("$id").delete().addOnFailureListener {
                Toast.makeText(context, " Failure Listener ", Toast.LENGTH_SHORT).show()

            }
                .addOnSuccessListener {
                    Log.d(TAG, "onViewCreated: ${args.dataPost.id}")
                    Log.d(TAG, "onViewCreated: ${args.dataPost.title}")
                    Toast.makeText(context, " Success Listener ", Toast.LENGTH_SHORT).show()
                }
        }
        binding.titleBottomTv.
        setText(args.dataPost.title)

        binding.updatePostiv.load(args.dataPost.postImageUrl)

        Log.d(TAG, "onViewCreated:${args.dataPost.postImageUrl} ")

        binding.updateDestv.
        setText(args.dataPost.description)//weee
        Log.d(TAG, "onViewCreated: ${args.dataPost.userId}")
        Log.d(TAG, "onViewCreated: ${args.dataPost.id}")
        if (userId.toString()!=args.dataPost.userId){
            Toast.makeText(context, "this is your post ", Toast.LENGTH_SHORT).show()
            binding.updateBtn.visibility=View.GONE
            binding.deleteBtn.visibility=View.GONE
            binding.titleBottomTv.isEnabled=false
            binding.updateDestv.isEnabled=false
        }
        binding.updateBtn.setOnClickListener {
            updateThePost()
        }
    }
    private fun updateThePost() {
        database.collection("Posts").document(args.dataPost.id)
            .update("title", "${binding.titleBottomTv.text}").addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Update The Post Successfully ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "there is error some where", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun de(title: String, id: String) {
        val desertRef = storage.child("image/$title/$id")
        desertRef.delete().addOnSuccessListener {
            Log.d(TAG, "onViewCreated: ${args.dataPost.postImageUrl} SuccessListener ")
        }.addOnFailureListener {
            Log.d(TAG, "onViewCreated: ${args.dataPost.postImageUrl} FailureListener ")

        }

    }
    fun addCommit(){
        val id=args.dataPost.id
        if (userId != null) {
            val iu="he"
//            database= FirebaseFirestore.getInstance()
//            database.collection("Posts").document(id).collection("comments").document(userId).set(iu)
         //   database.collection("Posts").document(id).collection("Commit").document(userId).set()
        }
    }
}