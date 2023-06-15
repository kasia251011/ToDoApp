package com.example.todoapp.ui.task.add

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.ui.notification.ReminderWorker
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * View Model to validate and insert tasks in the Room database.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AddTaskViewModel(private val tasksRepository: TasksRepository, application: Application) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)
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
            if (addTaskUiState.task.isNotificationEnable) {
                scheduleReminder(addTaskUiState.task)
            }
            tasksRepository.insertTask(addTaskUiState.task)
        }
    }

    private fun validateInput(uiState: Task = addTaskUiState.task): Boolean {
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
        null,
        null
    ),
    val isEntryValid: Boolean = false
)

/**
 * Extension function to convert [Task] to [AddTaskUiState]
 */


