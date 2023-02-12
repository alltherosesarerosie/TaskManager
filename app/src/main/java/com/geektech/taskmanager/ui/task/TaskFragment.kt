package com.geektech.taskmanager.ui.task

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.taskmanager.App
import com.geektech.taskmanager.R
import com.geektech.taskmanager.model.Task
import com.geektech.taskmanager.databinding.FragmentTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val db = Firebase.firestore
    private lateinit var navArgs: TaskFragmentArgs
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            navArgs = TaskFragmentArgs.fromBundle(it)
            task = navArgs.task
        }

        if (task != null) {
            binding.etTitle.setText(task?.title)
            binding.etDesc.setText(task?.desc)
            binding.btnSave.text = getString(R.string.update)
        } else {
            binding.btnSave.text = getString(R.string.save)
        }

        binding.btnSave.setOnClickListener {
            if (task != null) {
                onUpdate()
            } else onSave()
        }
//        getClick()
    }

    private fun onUpdate() {
//        getClick()
        task?.title = binding.etTitle.text.toString()
        task?.desc = binding.etDesc.text.toString()
        task?.let { App.db.taskDao().update(it) }
        findNavController().navigateUp()
    }

    private fun onSave() {
        val task = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString(),
        )
        putTask(task)
        App.db.taskDao().insertAll(task)
        findNavController().navigateUp()
    }

    private fun putTask(task: Task) {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            db.collection(it).add(task).addOnSuccessListener {
                Log.d("astra", "onSave: success")
            }.addOnFailureListener {
                Log.d("astra", "onSave: " + it.message)
            }
        }
    }

    private fun getClick() {
        val title = arguments?.getString("title")
        val desk = arguments?.getString("desc")
        binding.etTitle.setText(title)
        binding.etDesc.setText(desk)
    }
}
