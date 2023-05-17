package com.example.todoapp.ui.task.common

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TaskAppBar(
    title: String,
    navigateBack: () -> Unit,
    handleAction: () -> Unit,
    actionEnabled: Boolean
) {
    TopAppBar(
        title = { Text(title, fontSize = 15.sp) },
        navigationIcon = {
            IconButton(navigateBack) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Cancel"
                )
            }
        },
        backgroundColor = Color.White,
        actions = {
            Button(handleAction, enabled = actionEnabled) {
                Text(title, color = Color.White)
            }
        }
    )
}