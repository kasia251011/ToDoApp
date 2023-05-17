package com.example.todoapp.ui.task.edit

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.Task

@Composable
fun TaskDetailsAppBar(
    navigateBack: () -> Unit,
    navigate: (String) -> Unit,
    deleteTask: () -> Unit,
    task: Task

) {
    TopAppBar(
        title = { Text("Task details", fontSize = 15.sp) },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Cancel"
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            IconButton(onClick = {navigate("${EditTaskDestination.route}/${task.id}")}) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
            IconButton(onClick = deleteTask) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    )
}
