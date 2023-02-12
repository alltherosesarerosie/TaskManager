package com.geektech.taskmanager.data.db

import androidx.room.*
import com.geektech.taskmanager.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun insertAll(task: Task)

    @Delete
    fun delete(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)
}