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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tuwaiq.AndroidGeeks.database.Post.Posts
import com.tuwaiq.AndroidGeeks.databinding.NewPostFragmentBinding
import com.tuwaiq.AndroidGeeks.databinding.PostFragmentBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dp= FirebaseStorage.getInstance()
        database= FirebaseFirestore.getInstance()
        storage=dp.getReference()
        binding= PostFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

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
        setText(args.dataPost.title)//weee
        binding.updatePostiv.
        setImageURI(args.dataPost.postImageUrl.toUri())//weee
        binding.updateDestv.
        setText(args.dataPost.description)//weee
    }

    private fun de(title: String, id: String) {
        val desertRef = storage.child("image/$title/$id")
        desertRef.delete().addOnSuccessListener {
            Log.d(TAG, "onViewCreated: ${args.dataPost.postImageUrl} SuccessListener ")
        }.addOnFailureListener {
            Log.d(TAG, "onViewCreated: ${args.dataPost.postImageUrl} FailureListener ")

        }
    }
    val b="k".length
    val o=0
}