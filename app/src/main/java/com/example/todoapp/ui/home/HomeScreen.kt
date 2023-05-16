package com.example.todoapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.capitalizeWords
import com.example.todoapp.data.Task
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.task.details.TaskDetailsDestination
import com.example.todoapp.ui.theme.DarkGrey
import com.example.todoapp.ui.theme.ToDoAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "home"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navigateToTaskDetails: (String) -> Unit,
    navigateToAddTask: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddTask,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            taskList = homeUiState.itemList,
            onTaskClick = navigateToTaskDetails,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeBody (
    taskList: List<Task>,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .background(Color(0xFFF9F9F9))
            .padding(20.dp)
    ) {
        Header()
        if (taskList.isEmpty()) {
            Box(
                Modifier.fillMaxSize()) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "There is no tasks yet\nAdd one!",
                    modifier = Modifier.align(Alignment.Center))
            }
        } else {
            TaskList(taskList = taskList, onTaskClick = onTaskClick)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskList(
    taskList: List<Task>,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = taskList, key = { it.id }) { task ->
            TaskCard(task = task, onTaskClick = onTaskClick)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header () {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd")
        val dateTime = capitalizeWords(LocalDateTime.now().format(formatter))
        Column {
            Text("Your Tasks", style = MaterialTheme.typography.h1)
            Text(dateTime, color = DarkGrey)
        }
        Box (
            Modifier
                .clickable {/*TODO: Navigate to settings */ }
        ) {
            Icon(
                painter = painterResource(R.drawable.settings),
                "settings",
                tint = DarkGrey,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenRoutePreview() {
    val defaultDate =  LocalDateTime.parse("01-06-2022T11:30:10")
    ToDoAppTheme {
        HomeBody(
            listOf(
                Task(
                    1,
                    "Complete Project Proposal",
                    "Write a detailed project proposal for the upcoming conference",
                    defaultDate,
                    defaultDate,
                    isDone = false,
                    isNotificationEnable = true,
                    "Work"
                ),
                Task(
                    2,
                    "Buy Groceries",
                    "Purchase items for the week, including fruits, vegetables, and bread.",
                    defaultDate,
                    defaultDate,
                    isDone = true,
                    isNotificationEnable = true,
                    "Personal"
                ),
                Task(
                    3,
                    "Read Book",
                    "Finish reading 'The Great Gatsby' novel by F. Scott Fitzgerald.",
                    defaultDate,
                    defaultDate,
                    isDone = false,
                    isNotificationEnable = false,
                    "Leisure"
                ),
            ),
            onTaskClick = {}
        )
    }
}