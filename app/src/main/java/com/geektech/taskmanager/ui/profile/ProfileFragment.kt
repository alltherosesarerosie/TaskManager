package com.geektech.taskmanager.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.taskmanager.R
import com.geektech.taskmanager.data.Pref
import com.geektech.taskmanager.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.File


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        binding.etName.setText(pref.getName())
        binding.saveName.setOnClickListener {
            pref.saveName(binding.etName.text.toString())
            findNavController().navigateUp()
        }
        auth = FirebaseAuth.getInstance()
        binding.exit.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.authFragment)

        }
    }
}