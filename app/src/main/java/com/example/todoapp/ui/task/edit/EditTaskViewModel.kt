package com.example.todoapp.ui.task.edit

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todoapp.ui.task.details.TaskDetailsDestination
import com.example.todoapp.ui.task.details.TaskDetailsUiState
import com.example.todoapp.ui.task.details.toTaskUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * View Model to validate and insert tasks in the Room database.
 */
@RequiresApi(Build.VERSION_CODES.O)
class EditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository,
    ) : ViewModel() {


    var editTaskUiState by mutableStateOf(EditTaskUiState())
        private set

    private val taskId: Int = checkNotNull(savedStateHandle[EditTaskDestination.taskIdArg])

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                println("EDIT-----------------$taskId")
                editTaskUiState = tasksRepository.getTaskStream(taskId)
                    .filterNotNull()
                    .first()
                    .toEditTaskUiState()
            }
        }
    }

    fun updateTaskUiState(task: Task) {
        editTaskUiState = EditTaskUiState(task, validateInput(task))
    }


    suspend fun updateTask() {
        if (validateInput()) {
//            updateTaskUiState(editTaskUiState.task.copy(createDateTime = Calendar.getInstance()))
            tasksRepository.updateTask(editTaskUiState.task)
        }
    }

    private fun validateInput(uiState: Task = editTaskUiState.task): Boolean {
        return with(uiState) {
            title.isNotBlank() && category.isNotBlank()
        }
    }
}


/**
 * Represents Ui State for an Item.
 */
@RequiresApi(Build.VERSION_CODES.O)
data class EditTaskUiState constructor(
    val task: Task = Task(
        0,
        "",
        "",
       Calendar.getInstance(),
        Calendar.getInstance(),
        isDone = false,
        isNotificationEnable = false,
        ""),
    val isEntryValid: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun Task.toEditTaskUiState(): EditTaskUiState = EditTaskUiState(
    task = this,
    isEntryValid = false
)

