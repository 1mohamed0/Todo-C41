package com.route.todoc41.ui.home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todoc41.R
import com.route.todoc41.database.entity.Task
import com.route.todoc41.databinding.ActivityHomeBinding
import com.route.todoc41.ui.home.fragments.coentroltask.AddTaskFragment
import com.route.todoc41.ui.home.fragments.SettingsFragment
import com.route.todoc41.ui.home.fragments.tasks_fragment.TasksFragment
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setAppLanguage()
        setContentView(binding.root)
        setNavigation()
        setOnFabClick()
    }


    private fun setNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.tasks) {
                showFragment(TasksFragment())
                binding.title.text = getString(R.string.to_do_list)
            } else if (menuItem.itemId == R.id.settings) {
                showFragment(SettingsFragment())
                binding.title.text = getString(R.string.settings)
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.tasks
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .commit()
    }


    private fun setOnFabClick() {
        binding.fabAddTask.setOnClickListener {
            val bottomSheet = AddTaskFragment()
            bottomSheet.show(supportFragmentManager, "")
            bottomSheet.onTaskAddedListener = AddTaskFragment.OnTaskAddedListener { task: Task ->
                //reload data in recyclerview in TasksFragment
            }
        }

    }

    private fun setAppLanguage() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("Language", "en") ?: "en"

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}