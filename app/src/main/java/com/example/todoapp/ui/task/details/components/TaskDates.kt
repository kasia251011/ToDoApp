package com.example.todoapp.ui.task.details.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.todoapp.calendarToString
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Grey
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDates(task: Task) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Due: ${calendarToString(task.dueDateTime)}")
        Text("Created: ${calendarToString(task.dueDateTime)}", color = Grey, fontSize = 10.sp)
    }
}
