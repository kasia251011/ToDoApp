package com.example.todoapp.ui.task.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.task.details.components.TaskDates
import com.example.todoapp.ui.task.details.components.TaskHeader
import com.example.todoapp.ui.theme.*
import kotlinx.coroutines.launch

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
    val taskDetailsUiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                navigateBack = navigateBack,
                deleteTask = {
                    coroutineScope.launch {
                        navigateBack()
                        viewModel.deleteTask()

                    }
                },
                navigate = navigate,
                task = taskDetailsUiState.task,
            )
        }
    ) { innerPadding ->
        TaskDetailsBody(
            task = taskDetailsUiState.task,
            modifier = Modifier.padding(innerPadding),
            updateState = viewModel::updateState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskDetailsBody(
    task: Task,
    modifier: Modifier = Modifier,
    updateState: (Task) -> Unit
) {
    Column(
        modifier = modifier
            .padding(25.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TaskHeader(task, updateState)
        Text(task.description)
        TaskDates(task)
        Divider(color = LightGrey)
        //TODO: Display files

    }
}