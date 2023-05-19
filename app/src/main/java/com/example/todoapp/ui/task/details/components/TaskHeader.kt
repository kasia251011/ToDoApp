package com.example.todoapp.ui.task.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Blue
import com.example.todoapp.ui.theme.DarkGrey
import com.example.todoapp.ui.theme.LightGrey

@Composable
fun TaskHeader(
    task: Task,
    updateState: (Task) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(25.dp)
    ) {
        Column {
            Text(task.title, style = MaterialTheme.typography.h1)
            Text(
                task.category,
                color = DarkGrey,
                modifier =  Modifier.padding(top = 5.dp).height(200.dp))
        }
        if(task.isDone) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Blue)
                    .padding(10.dp)
                    .clickable {
                        updateState(task.copy(isDone = !task.isDone))
                    }) {
                Icon(
                    painter = painterResource(R.drawable.done),
                    "done",
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
            }
        } else {
            Box(
                Modifier
                    .border(1.dp, LightGrey, shape = CircleShape)
                    .size(35.dp)
                    .clickable {
                        updateState(task.copy(isDone = !task.isDone))
                    }) {
            }
        }
    }
}
