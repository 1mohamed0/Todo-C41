package com.route.todoc41.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoc41.database.dao.TasksDao
import com.route.todoc41.database.entity.Task


@Database(entities = [Task::class], version = 2, exportSchema = true)

abstract class MyDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private var instance: MyDatabase? = null

        fun init(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDatabase::class.java, "tasksDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

}