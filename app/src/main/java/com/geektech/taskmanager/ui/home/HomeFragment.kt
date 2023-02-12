package com.geektech.taskmanager.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.geektech.taskmanager.utils.isOnline
import com.geektech.taskmanager.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter
    private val db = Firebase.firestore

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::onClick)
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
        if (requireContext().isOnline()) {
            getTasks()
        } else {
            setData()
        }
        initAdapter()
        onLongClick()
    }

    private fun getTasks() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            db.collection(uid).get().addOnSuccessListener {
                val data = it.toObjects(Task::class.java)
                adapter.addTask(data)
            }.addOnFailureListener {
            }
        }
    }

    private fun onClick(task: Task) {
        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskFragment(task))
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
                showToast("Successfully deleted!")
                App.db.taskDao().delete(it)
                setData()
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