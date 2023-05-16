package com.example.todoapp.ui.task.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Category
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.ui.task.add.AddTaskUiState

@RequiresApi(Build.VERSION_CODES.O)
class TaskDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository,
) : ViewModel() {


    var uiState by mutableStateOf(TaskDetailsUiState())
        private set

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailsDestination.taskIdArg])

    init {
        viewModelScope.launch {
            uiState = tasksRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState()
        }
    }

    fun updateState(task: Task) {
        viewModelScope.launch {
            uiState = TaskDetailsUiState(task)
            tasksRepository.updateTask(task)
        }

    }

    suspend fun deleteTask() {
        tasksRepository.deleteTask(uiState.task)
    }

    //TODO: Upload file

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for TaskDetailsScreen
 */
@RequiresApi(Build.VERSION_CODES.O)
data class TaskDetailsUiState  constructor(
    val task: Task = Task(
        0,
        "",
        "",
        LocalDateTime.parse("2022-01-06T21:30:10"),
        LocalDateTime.parse("2022-01-06T21:30:10"),
        isDone = false,
        isNotificationEnable = false,
        ""),
)

@RequiresApi(Build.VERSION_CODES.O)
fun Task.toTaskUiState(): TaskDetailsUiState = TaskDetailsUiState(
    task = this
)
