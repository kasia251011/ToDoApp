package com.example.todoapp.ui.task.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black

@Composable
fun NotificationEnableToggle(task: Task, updateState: (Task) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Icon(
                painter = painterResource(R.drawable.notify),
                "notify",
                tint = Black,
                modifier = Modifier.size(25.dp)
            )
            Text("Notifications")
        }

        Switch(
            checked = task.isNotificationEnable,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue, checkedTrackColor = Color(0xFF71A7E7)),
            onCheckedChange = { updateState(task.copy(isNotificationEnable = !task.isNotificationEnable),) }
        )
    }
}