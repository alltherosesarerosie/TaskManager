package com.geektech.taskmanager.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.taskmanager.model.Task
import com.geektech.taskmanager.databinding.ItemTaskBinding

class TaskAdapter : Adapter<TaskAdapter.TaskViewHolder>() {
    var onLongClick: ((Task) -> Unit)? = null
    var onClick: ((Task) -> Unit)? = null
    private var data = arrayListOf<Task>()

/*
    fun addTask(task: Task){
        data.add(0,task)
        notifyItemChanged(0)
        println(data)
    }
*/

    fun addTask(list: List<Task>) {
        data = list as ArrayList<Task>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.desc
            itemView.setOnLongClickListener {
                onLongClick?.invoke(task)
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener {
                onClick?.invoke(task)

            }
        }
    }

}