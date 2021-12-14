package com.tuwaiq.blogerrtest.newpost

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.tuwaiq.blogerrtest.R

class NewPostFragment : Fragment() {

    private lateinit var titleEt:EditText
    private lateinit var postEt:EditText
    private lateinit var postBtn:Button

    private lateinit var viewModel: NewPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.new_post_fragment, container, false)

        titleEt=view.findViewById(R.id.title_tv)
        postEt=view.findViewById(R.id.post_et)
        postBtn=view.findViewById(R.id.post_btn)

        if (titleEt.text.isNotEmpty()){
//            postBtn.isClickable=true

       }

        return view
    }



}