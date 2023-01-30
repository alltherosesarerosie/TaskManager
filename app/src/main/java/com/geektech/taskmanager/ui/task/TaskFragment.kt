package com.geektech.taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.geektech.taskmanager.App
import com.geektech.taskmanager.model.Task
import com.geektech.taskmanager.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {
    private lateinit var
            binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            App.db.taskDao().insertAll(
                Task(
                    title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString(),
                )
            )
            findNavController().navigateUp()
        }
        getClick()
    }

    private fun getClick() {
        val title = arguments?.getString("title")
        val desk = arguments?.getString("desc")
        binding.etTitle.setText(title)
        binding.etDesc.setText(desk)
    }

}
