package com.geektech.taskmanager.ui.accept

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geektech.taskmanager.R
import com.geektech.taskmanager.databinding.FragmentAcceptBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AcceptFragment : Fragment() {
    private lateinit var binding: FragmentAcceptBinding
    private lateinit var args: AcceptFragmentArgs
    private lateinit var auth: FirebaseAuth
    private var code = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAcceptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        args = arguments?.let { AcceptFragmentArgs.fromBundle(it) }!!
        binding.send.setOnClickListener {
            code = binding.etCode.text.toString()
            val credential = PhoneAuthProvider.getCredential(args.verId, code)
            signInWithPhoneAuthCredential(credential)
            findNavController().navigate(AcceptFragmentDirections.actionAcceptFragmentToNavigationHome())
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.navigation_home)
                    val user = task.result?.user
                } else {
                    Log.w(TAG, "shamal", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}