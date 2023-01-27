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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter:TaskAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        Click()
onLongClick()
    }

    fun Click(){
        adapter.onClick ={
            val bundle=Bundle()
            bundle.putString("title", it.title)
            bundle.putString("desc", it.desc)
            findNavController().navigate(R.id.taskFragment, bundle)
        }
    }

    fun onLongClick(){
        adapter.onLongClick={
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Android Alert")
//            builder.setMessage("We have a message")
////builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
//
//            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                Toast.makeText(applicationContext,
//                    android.R.string.yes, Toast.LENGTH_SHORT).show()
//            }
//
//            builder.setNegativeButton(android.R.string.no) { dialog, which ->
//                Toast.makeText(applicationContext,
//                    android.R.string.no, Toast.LENGTH_SHORT).show()
//            }
//
//            builder.show()
            val bundle=Bundle()
            onCreateDialog(bundle)

            App.db.taskDao().delete(it)
        }
    }

    fun initAdapter(){
        val tasks = App.db.taskDao().getAll()
        adapter.addTask(tasks)
        binding.recycleView.adapter=adapter
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.taskFragment)
        }
    }

    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("deleting task")
                .setMessage("Are you sure")
                .setPositiveButton("YES") {
                        dialog, id ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}