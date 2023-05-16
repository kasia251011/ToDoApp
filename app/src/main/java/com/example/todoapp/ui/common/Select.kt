package com.example.todoapp.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapp.Category
import com.example.todoapp.data.Task

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Select (
    label: String,
    options: List<Category>,
    onValueChange: (Task) -> Unit = {},
    task: Task
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0].name) }
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
            onValueChange = { },
            decorationBox = {innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    innerTextField()
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
//                        ExposedDropdownMenuDefaults.TrailingIcon(
//                            expanded = expanded
//                        ),
                        modifier = Modifier.size(24.dp),
                        contentDescription = null,
                        tint = Color(0xFFA8A8A8),
                    )
                }
            }
//            label = { Text(label) },
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(
//                    expanded = expanded
//                )
//            },
//            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption.name
                        expanded = false

                        onValueChange(task.copy(category = selectedOptionText))

                    }
                ) {
                    Text(selectionOption.name)
                }
            }
        }
    }
}