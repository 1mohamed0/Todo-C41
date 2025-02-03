package com.route.todoc41.ui.home.fragments.tasks_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todo.ui.home.adapter.TasksAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todoc41.R
import com.route.todoc41.database.MyDatabase
import com.route.todoc41.database.dao.TasksDao
import com.route.todoc41.database.entity.Task
import com.route.todoc41.databinding.FragmentTasksBinding
import com.route.todoc41.ui.home.TaskEditeActivity
import com.route.todoc41.ui.util.Constant
import com.route.todoc41.ui.util.clearTime
import java.util.Calendar

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var dao: TasksDao
    private var tasks = mutableListOf<Task>()
    private var tasksAdapter = TasksAdapter()
    private var selectedDate: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initCalenderView()
        initSelectedDate()


    }




    private fun initSelectedDate() {
        val calendar = Calendar.getInstance()
        val today = CalendarDay.today()
        calendar.set(Calendar.YEAR, today.year)
        calendar.set(Calendar.MONTH, today.month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, today.day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        selectedDate = calendar.timeInMillis
    }

    private fun initCalenderView() {
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, date.year)
            calendar.set(Calendar.MONTH, date.month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date.day)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            if (selected) {
                val tasks = dao.getAllTasksByDate(calendar.timeInMillis).toMutableList()
                tasksAdapter.updateTasks(tasks)
            }
        }

    }

    private fun initRecyclerView() {
        binding.rvTasks.adapter = tasksAdapter
        tasksAdapter.onButtonClickedListener =
            TasksAdapter.OnItemClickedListener { position, task ->
                task.isDone = !task.isDone
                dao.updateTask(task)
                tasksAdapter.updateTask(task, position)
            }

        tasksAdapter.onDeleteClickedListener =
            TasksAdapter.OnItemClickedListener { _, task ->
                dao.deleteTask(task)
                tasksAdapter.deleteTask(task)
            }
        tasksAdapter.onItemClickedListener =
            TasksAdapter.OnItemClickedListener { _, task ->
                val intent = Intent(activity, TaskEditeActivity::class.java)
                intent.putExtra(Constant.TASK_KEY, task)
                startActivity(intent)
            }


    }



    override fun onStart() {
        super.onStart()
        activity?.let { dao = MyDatabase.init(it.applicationContext).tasksDao() }
        loadAllTasksOfDate(getSelectedDate().timeInMillis)
        tasksAdapter.setColor(ContextCompat.getColor(requireContext(), R.color.blue))

    }


    fun loadAllTasksOfDate(date: Long) {
        tasks = dao.getAllTasksByDate(date).toMutableList()
        tasksAdapter.updateTasks(tasks)


    }
    private fun getSelectedDate(): Calendar {
        val calendar = Calendar.getInstance()
        if (binding.calendarView.selectedDate != null) {
            calendar.set(Calendar.YEAR, binding.calendarView.selectedDate!!.year)
        }
        binding.calendarView.selectedDate?.let { date ->
            calendar.set(Calendar.YEAR, date.year)
            calendar.set(Calendar.MONTH, date.month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date.day)
        }
        calendar.clearTime()
        return calendar
    }

}