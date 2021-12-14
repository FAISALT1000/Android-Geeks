package com.tuwaiq.blogerrtest.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.blogerrtest.MainActivityForTesting
import com.tuwaiq.blogerrtest.R
import com.tuwaiq.blogerrtest.databinding.FragmentDashboardBinding

private const val TAG = "DashboardFragment"
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var emailTv:TextView
    private lateinit var passTv:TextView
    private lateinit var textView: TextView
    private lateinit var logoutBtn:Button
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)
//        dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

//        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//         textView= binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        logoutBtn=view.findViewById(R.id.logoutbtn)

        logoutBtn.setOnClickListener {
            Log.d(TAG,"Logout")
            Toast.makeText(context,"Logout successful", Toast.LENGTH_LONG).show()
            val intent=Intent(context,MainActivityForTesting::class.java)
            startActivity(intent)



        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}