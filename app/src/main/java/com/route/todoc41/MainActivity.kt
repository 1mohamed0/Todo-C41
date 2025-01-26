package com.route.todoc41

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.route.todoc41.database.MyDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val getAllQuery = "select * from Task"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}