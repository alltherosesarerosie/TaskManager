package com.geektech.taskmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Int= 0,
    var title:String?=null,
    var desc:String?=null
):java.io.Serializable
