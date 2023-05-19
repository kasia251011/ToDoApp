package com.example.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Suppress("ArrayInDataClass")
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val createDateTime: Calendar,
    val dueDateTime: Calendar,
    var isDone: Boolean,
    val isNotificationEnable: Boolean,
    val category: String,
    @ColumnInfo(name = "file",typeAffinity = ColumnInfo.BLOB)
    val file: ByteArray?,
)