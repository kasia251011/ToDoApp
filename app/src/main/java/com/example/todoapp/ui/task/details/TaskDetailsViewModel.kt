package com.example.todoapp.ui.task.details

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class TaskDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository,
    application: Application
) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    private val _uiState = MutableStateFlow(TaskDetailsUiState())
    val uiState: StateFlow<TaskDetailsUiState> = _uiState

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailsDestination.taskIdArg])

    init {
        viewModelScope.launch {
            tasksRepository.getTaskStream(taskId)
                .collect { task ->
                    _uiState.value = task?.let { TaskDetailsUiState(it) } ?: TaskDetailsUiState()
                }
        }
    }

    fun updateState(task: Task) {
        viewModelScope.launch {
            tasksRepository.updateTask(task)
        }
    }

    suspend fun deleteTask() {

        viewModelScope.launch {
            uiState.value.task.notificationId?.let { workManager.cancelWorkById(it) }
            tasksRepository.deleteTask(uiState.value.task)

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
data class TaskDetailsUiState  constructor(
    val task: Task = Task(
        0,
        "",
        "",
        Calendar.getInstance(),
        Calendar.getInstance(),
        isDone = false,
        isNotificationEnable = false,
        "",
    null,
        null
    ),
)