package com.example.todoapp.ui.task.details.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.Category
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.common.Select
import com.example.todoapp.ui.theme.Black

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChangeTaskCategory(task: Task, updateState: (Task) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        val categoryOptions = listOf(Category.Home, Category.Office, Category.Other, Category.School)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.category),
                "category",
                tint = Black,
                modifier = Modifier.size(20.dp)
            )
            Text("Category", Modifier.padding(start = 10.dp))
        }

        Select(label = "", options = categoryOptions, task = task, onValueChange = updateState)
    }
}