package com.example.todoapp.ui.task.edit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.task.common.*
import com.example.todoapp.ui.theme.LightGrey
import com.example.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch
import java.util.*

object EditTaskDestination : NavigationDestination {
    override val route = "edit_task"
    override val title = "Edit Task"
    const val taskIdArg = "taskIdd"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: EditTaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TaskAppBar(
                navigateBack = navigateBack,
                title= "Edit Task",
                actionEnabled = viewModel.editTaskUiState.isEntryValid,
                handleAction = {
                    coroutineScope.launch {
                        viewModel.updateTask()
                        navigateBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        AddTaskBody(
            task = viewModel.editTaskUiState.task,
            updateTaskUiState = viewModel::updateTaskUiState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskBody(
    task: Task,
    updateTaskUiState: (Task) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TitleTextField(task, updateTaskUiState)
            Divider(color = LightGrey)
            DescriptionTextField(task, updateTaskUiState)
            Divider(color = LightGrey)
            CategorySelect(task, updateTaskUiState)
            Divider(color = LightGrey)
            DateTimePicker(task, updateTaskUiState)
            Divider(color = LightGrey)
            NotificationEnableToggle(task, updateTaskUiState)
            Divider(color = LightGrey)
            FileAdd(task, updateTaskUiState )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItemEditRoutePreview() {
    ToDoAppTheme {
        EditTaskScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}