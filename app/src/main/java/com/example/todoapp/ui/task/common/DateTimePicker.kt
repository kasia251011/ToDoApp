package com.example.todoapp.ui.task.add.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black
import java.util.*

@Composable
fun DateTimePicker(task: Task, updateTaskUiState: (Task) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Icon(
                painter = painterResource(R.drawable.date),
                modifier = Modifier.size(25.dp),
                contentDescription = null,
                tint = Black,
            )
            DatePicker(task, updateTaskUiState)
        }
        TimePicker(task, updateTaskUiState)
    }
}

@Composable
fun DatePicker(task: Task, updateTaskUiState: (Task) -> Unit) {
    val context = LocalContext.current

    val year = task.dueDateTime[Calendar.YEAR]
    val month = task.dueDateTime[Calendar.MONTH]
    val dayOfMonth = task.dueDateTime[Calendar.DAY_OF_MONTH]

    var selectedDateText by remember { mutableStateOf("$dayOfMonth/$month/$year") }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->

            val newTask = task.copy()
            newTask.dueDateTime.set(Calendar.YEAR, selectedYear)
            newTask.dueDateTime.set(Calendar.MONTH, selectedMonth)
            newTask.dueDateTime.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

            updateTaskUiState(newTask)

            val formattedDay = if (selectedDayOfMonth + 1 < 10) "0$selectedDayOfMonth" else selectedDayOfMonth
            val formattedMonth = if (selectedMonth + 1 < 10) "0$selectedMonth" else selectedMonth
            val formattedYear = selectedYear.toString().drop(2)
            selectedDateText =
                "$formattedDay/${formattedMonth}/$formattedYear"
        }, year, month, dayOfMonth
    )

    Text(selectedDateText, Modifier.clickable { datePicker.show() })
}

@Composable
fun TimePicker(task: Task, updateTaskUiState: (Task) -> Unit) {
    val context = LocalContext.current

    // Fetching current hour, and minute
    val hour = task.dueDateTime[Calendar.HOUR_OF_DAY]
    val minute = task.dueDateTime[Calendar.MINUTE]

    var selectedTimeText by remember { mutableStateOf("$hour:$minute") }

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->

            val newTask = task.copy()
            newTask.dueDateTime.set(Calendar.HOUR_OF_DAY, selectedHour)
            newTask.dueDateTime.set(Calendar.MINUTE, selectedMinute)

            updateTaskUiState(newTask)

            selectedTimeText = "$selectedHour:${if (selectedMinute < 10) "00" else selectedMinute}"
        }, hour, minute, false
    )

    Text(selectedTimeText, Modifier.clickable { timePicker.show() })
}