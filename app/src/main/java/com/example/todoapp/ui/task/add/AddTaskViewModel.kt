package com.example.todoapp.ui.task.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model to validate and insert tasks in the Room database.
 */
class AddTaskViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    /**
     * Holds current task ui state
     */
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    /**
     * Updates the [taskUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    init {
        viewModelScope.launch {
            taskUiState = Task(
                0,
                "",
                "",
                "",
                "",
                isDone = false,
                isNotificationEnable = false,
                "").toTaskUiState()
        }
    }


    fun updateUiState(task: Task) {
        taskUiState = TaskUiState(task, validateInput(task))
    }


    /**
     * Inserts an [Task] in the Room database
     */
    suspend fun saveTask() {
        if (validateInput()) {
            tasksRepository.insertTask(taskUiState.task)
        }
    }

    private fun validateInput(uiState: Task = taskUiState.task): Boolean {
        return with(uiState) {
            title.isNotBlank() && category.isNotBlank()
        }
    }
}


/**
 * Represents Ui State for an Item.
 */
data class TaskUiState(
    val task: Task = Task(
        0,
        "",
        "",
        "",
        "",
        isDone = false,
        isNotificationEnable = false,
        ""),
    val isEntryValid: Boolean = false
)

/**
 * Extension function to convert [Task] to [TaskUiState]
 */
fun Task.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    task = this,
    isEntryValid = isEntryValid
)

