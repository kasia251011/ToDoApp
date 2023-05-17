package com.example.todoapp.ui.task.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.task.details.components.TaskDates
import com.example.todoapp.ui.task.details.components.TaskHeader
import com.example.todoapp.ui.task.edit.TaskDetailsAppBar
import com.example.todoapp.ui.theme.*
import kotlinx.coroutines.launch
import java.util.Calendar

object TaskDetailsDestination : NavigationDestination {
    override val route = "task_details"
    override val title = "Task Details"
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailsScreen(
    navigate: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: TaskDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val task = viewModel.uiState.task
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                navigateBack = navigateBack,
                deleteTask = { },
                navigate = navigate,
                task = task
            )
        }
    ) { innerPadding ->
        TaskDetailsBody(
            task = task,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteTask()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding),
            updateState = viewModel::updateState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskDetailsBody(
    task: Task,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    updateState: (Task) -> Unit
) {
    Column(
        modifier = modifier
            .padding(25.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        TaskHeader(task, updateState)
        Text(task.description)
        TaskDates(task)
        Divider(color = LightGrey)
        //TODO: Display files


        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
    }
}





@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete task") },
        text = { Text("Are you sure you want to delete?") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Delete")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    ToDoAppTheme {
        val defaultDate =  Calendar.getInstance()
        TaskDetailsBody(
            task = Task(
                3,
                "Read Book",
                "Finish reading 'The Great Gatsby' novel by F. Scott Fitzgerald.",
                defaultDate,
                defaultDate,
                isDone = false,
                isNotificationEnable = false,
                "Leisure"
            ),
            onDelete = {},
            updateState = {}
        )
    }
}