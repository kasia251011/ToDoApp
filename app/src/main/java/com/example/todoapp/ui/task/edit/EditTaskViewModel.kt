package com.example.todoapp.ui.task.edit

import android.app.Application
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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.ui.notification.ReminderWorker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * View Model to validate and insert tasks in the Room database.
 */
@RequiresApi(Build.VERSION_CODES.O)
class EditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository,
    application: Application
    ) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    var editTaskUiState by mutableStateOf(EditTaskUiState())
        private set

    private val taskId: Int = checkNotNull(savedStateHandle[EditTaskDestination.taskIdArg])

    init {
        viewModelScope.launch {
            viewModelScope.launch {
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
            if (editTaskUiState.task.isNotificationEnable) {
                scheduleReminder(editTaskUiState.task)
            } else {
                editTaskUiState.task.notificationId?.let { workManager.cancelWorkById(it) }
                updateTaskUiState(editTaskUiState.task.copy(notificationId = null))
            }
            tasksRepository.updateTask(editTaskUiState.task)
        }
    }

    private fun validateInput(uiState: Task = editTaskUiState.task): Boolean {
        return with(uiState) {
            title.isNotBlank() && category.isNotBlank()
        }
    }

    private fun scheduleReminder(
        task: Task
    ) {
        // create a Data instance with the task title passed to it
        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
        myWorkRequestBuilder.setInputData(
            workDataOf(
                "NAME" to task.title
            )
        )

        val duration = task.dueDateTime.timeInMillis -  System.currentTimeMillis()

        myWorkRequestBuilder.setInitialDelay(duration, TimeUnit.MILLISECONDS)
        val work = myWorkRequestBuilder.build()

        updateTaskUiState(task.copy(notificationId = work.id))

        workManager.enqueue(work)
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
        "",
    null,
        null
    ),
    val isEntryValid: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun Task.toEditTaskUiState(): EditTaskUiState = EditTaskUiState(
    task = this,
    isEntryValid = false
)

