package com.route.todoc41.ui.home.fragments.tasks_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todoc41.database.entity.Task
import com.route.todoc41.databinding.ItemTaskBinding
import com.route.todoc41.ui.util.getFormattedTime
import java.util.Calendar

class TasksAdapter:RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var tasksList = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTasksList(tasks:MutableList<Task>){
        tasksList = tasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task:Task){
           binding.title.text = task.title
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = task.time
            val hr = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
           binding.time.text = getFormattedTime(hr, minutes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList[position]
        holder.bind(task)
    }
}