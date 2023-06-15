package com.example.todoapp.ui.task.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.Category
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.ui.theme.Black

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorySelect (
    task: Task,
    onValueChange: (Task) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    val categoryOptions = listOf(Category.Home, Category.Office, Category.Other, Category.School)
    var selectedOptionText by remember { mutableStateOf(task.category) }

    onValueChange(task.copy(category = selectedOptionText))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        BasicTextField(
            readOnly = true,
            value = selectedOptionText,
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onValueChange = { },
            decorationBox = {innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.category),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null,
                        tint = Black,
                    )
                    innerTextField()
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },

        ) {
            categoryOptions.forEach { categoryOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = categoryOption.name
                        expanded = false

                        onValueChange(task.copy(category = selectedOptionText))

                    },

                ) {
                    Text(categoryOption.name)
                }
            }
        }
    }
}