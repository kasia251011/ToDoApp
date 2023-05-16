package com.example.todoapp.ui.task.details.components

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
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Grey
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDates(task: Task) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth()
    ) {
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        Text("Due: ${task.dueDateTime.format(dateFormat)}")
        Text("Created: ${task.dueDateTime.format(dateFormat)}", color = Grey, fontSize = 10.sp)
    }
}
