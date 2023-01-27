package com.geektech.taskmanager

import android.app.Application
import androidx.room.Room
import com.geektech.taskmanager.data.db.AppDatabase
import com.geektech.taskmanager.data.db.TaskDao

class App: Application() {
    companion object{
        lateinit var db: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
         db = Room.databaseBuilder(
            applicationContext,
             AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
             .build()

    }


}
