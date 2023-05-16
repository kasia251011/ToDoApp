package com.example.todoapp.ui.task.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.Category
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.ToDoTopAppBar
import com.example.todoapp.ui.common.Select
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch
import java.util.*

object AddTaskDestination : NavigationDestination {
    override val route = "task_add"
    override val title = "Add New Task"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: AddTaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ToDoTopAppBar(
                title = "Add New Task",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        AddTaskBody(
            taskUiState = viewModel.addTaskUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTask()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskBody(
    taskUiState: AddTaskUiState,
    onItemValueChange: (Task) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        ItemInputForm( onValueChange = onItemValueChange, task = taskUiState.task)
        Button(
            onClick = onSaveClick,
            enabled = taskUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemInputForm(
    task: Task,
    modifier: Modifier = Modifier,
    onValueChange: (Task) -> Unit = {}
) {
    val categoryOptions = listOf(Category.Home, Category.Office, Category.Other, Category.School)
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = task.title ,
            onValueChange = { onValueChange(task.copy(title = it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = task.description,
            onValueChange = { onValueChange(task.copy(description = it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 10,
        )
        Select(label = "Category", options = categoryOptions, task = task, onValueChange = onValueChange)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItemEditRoutePreview() {
    ToDoAppTheme {
        AddTaskScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}