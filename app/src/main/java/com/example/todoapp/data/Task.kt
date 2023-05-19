package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val createDateTime: Calendar,
    val dueDateTime: Calendar,
    var isDone: Boolean,
    val isNotificationEnable: Boolean,
    val category: String,
    val filePath: String,
)