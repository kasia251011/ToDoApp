package com.example.todoapp.ui.task.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * View Model to validate and insert tasks in the Room database.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AddTaskViewModel(private val tasksRepository: TasksRepository) : ViewModel() {


    var addTaskUiState by mutableStateOf(AddTaskUiState())
        private set

    init {
        viewModelScope.launch {
            addTaskUiState = AddTaskUiState()
        }
    }

    fun updateTaskUiState(task: Task) {
        addTaskUiState = AddTaskUiState(task, validateInput(task))
    }


    suspend fun saveTask() {
        if (validateInput()) {
//            updateTaskUiState(addTaskUiState.task.copy())
            tasksRepository.insertTask(addTaskUiState.task)
        }
    }

    private fun validateInput(uiState: Task = addTaskUiState.task): Boolean {
        return with(uiState) {
            title.isNotBlank() && category.isNotBlank()
        }
    }
}


/**
 * Represents Ui State for an Item.
 */
@RequiresApi(Build.VERSION_CODES.O)
data class AddTaskUiState constructor(
    val task: Task = Task(
        0,
        "",
        "",
        Calendar.getInstance(),
        Calendar.getInstance(),
        isDone = false,
        isNotificationEnable = false,
        "",
        ""),
    val isEntryValid: Boolean = false
)

/**
 * Extension function to convert [Task] to [AddTaskUiState]
 */


