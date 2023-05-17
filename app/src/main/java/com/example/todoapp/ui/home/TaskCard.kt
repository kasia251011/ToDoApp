package com.example.todoapp.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.calendarToString
import com.example.todoapp.data.Task
import com.example.todoapp.ui.task.details.TaskDetailsDestination
import com.example.todoapp.ui.theme.Blue
import com.example.todoapp.ui.theme.DarkGrey
import com.example.todoapp.ui.theme.LightGrey
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskCard(
    task: Task,
    onTaskClick: (String) -> Unit,
) {
    Column(
        Modifier
            .padding(0.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(20.dp)
            .fillMaxWidth()
            .clickable { onTaskClick("${TaskDetailsDestination.route}/${task.id}") }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(task.title, fontSize = 18.sp)
                Text(task.category, color = DarkGrey, fontSize = 15.sp, modifier =  Modifier.padding(top = 5.dp))
            }
            if(task.isDone) {
                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(Blue)
                        .padding(10.dp)) {
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
                        .size(35.dp)) {
                }
            }

        }
        Divider(Modifier.padding(top = 20.dp, bottom = 20.dp), color = LightGrey)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){

            Text(
                "Due: ${calendarToString(task.dueDateTime)}",
                color = DarkGrey,
                fontSize = 15.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically,) {
                Icon(
                    painter = painterResource(R.drawable.attachemnt),
                    "attachment",
                    tint = DarkGrey,
                    modifier = Modifier.size(15.dp)
                )
                Text("0 Attachments", Modifier.padding(start = 10.dp), color = DarkGrey, fontSize = 15.sp)
            }
        }
    }
}