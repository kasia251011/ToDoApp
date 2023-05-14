package com.example.todoapp.ui.task.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.ToDoTopAppBar
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch

object AddTaskDestination : NavigationDestination {
    override val route = "task_add"
    override val title = "Add New Task"
}

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
            taskUiState = viewModel.taskUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTask()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AddTaskBody(
    taskUiState: TaskUiState,
    onItemValueChange: (Task) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
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
@Composable
fun ItemInputForm(
    task: Task,
    modifier: Modifier = Modifier,
    onValueChange: (Task) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = task.title ,
            onValueChange = { onValueChange(task.copy(title = it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = task.category,
            onValueChange = { onValueChange(task.copy(category = it)) },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEditRoutePreview() {
    ToDoAppTheme {
        AddTaskScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}