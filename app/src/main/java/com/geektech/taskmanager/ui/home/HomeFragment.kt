package com.geektech.taskmanager.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.geektech.taskmanager.App
import com.geektech.taskmanager.R
import com.geektech.taskmanager.model.Task
import com.geektech.taskmanager.databinding.FragmentHomeBinding
import com.geektech.taskmanager.ui.home.adapter.TaskAdapter
import com.geektech.taskmanager.ui.task.TaskFragment
import com.geektech.taskmanager.utils.showToast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        onLongClick()
    }

    private fun onLongClick() {
        adapter.onLongClick = {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Deleting task")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(
                    requireContext(),
                    android.R.string.yes, Toast.LENGTH_SHORT
                ).show()
                App.db.taskDao().delete(it)
                setData()
                showToast("Successfully deleted!")
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    requireContext(),
                    android.R.string.no, Toast.LENGTH_SHORT
                ).show()
            }
            builder.create().show()
        }
    }

    private fun initAdapter() {
        setData()
        binding.recycleView.adapter = adapter
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun setData() {
        val tasks = App.db.taskDao().getAll()
        adapter.addTask(tasks)
    }
}