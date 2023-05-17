package com.example.todoapp.ui.task.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black

@Composable
fun TitleTextField (task: Task, updateTaskUiState: (Task) -> Unit) {
    BasicTextField(
        value = task.title ,
        onValueChange = { updateTaskUiState(task.copy(title = it)) },
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp,  top = 16.dp, end = 16.dp),
        textStyle = MaterialTheme.typography.h1,
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                    tint = Color.Transparent,
                )
                Box {
                    if(task.title.isEmpty()) {
                        Text(
                            text = "Add title",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}