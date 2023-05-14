package com.example.todoapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.navigation.ToDoNavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ToDoApp(navController: NavHostController = rememberNavController()) {
    ToDoNavHost(navController = navController)
}

@Composable
fun ToDoTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    } else {
        TopAppBar(title = { Text(title) })
    }
}