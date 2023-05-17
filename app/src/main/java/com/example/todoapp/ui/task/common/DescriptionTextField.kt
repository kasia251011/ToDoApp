package com.example.todoapp.ui.task.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
fun DescriptionTextField (task: Task, updateTaskUiState: (Task) -> Unit) {
    BasicTextField(
        value = task.description ,
        onValueChange = { updateTaskUiState(task.copy(description = it)) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        textStyle = MaterialTheme.typography.body1,
        maxLines = 5,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.desc),
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                    tint = Black,
                )
                Box {
                    if(task.description.isEmpty()) {
                        Text(
                            text = "Add description",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}