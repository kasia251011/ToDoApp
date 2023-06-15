package com.example.todoapp.ui.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.Category
import com.example.todoapp.Delay
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import com.example.todoapp.ui.notification.ReminderWorker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val tasksRepository: TasksRepository,
    application: Application
) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    var filtersState by mutableStateOf(FiltersState())
        private set
    var delayState by mutableStateOf(Delay.MIN1)
        private set


    init {
        viewModelScope.launch {
            tasksRepository.getAllTasksStream()
                .collect { tasks ->
                    _uiState.value = HomeUiState(tasks)
                }
        }
    }


    fun filterTasks(searchText: String?, categories: List<String>?, doneTasksVisible: Boolean?) {

        viewModelScope.launch {
            filtersState = FiltersState(
                searchText ?: filtersState.searchText,
                categories ?: filtersState.categories,
                doneTasksVisible ?: filtersState.onlyDoneTasksVisible)

            tasksRepository.getAllTasksStream()
                .collect { tasks ->
                    _uiState.value = HomeUiState(tasks)
                    _uiState.value = HomeUiState(_uiState.value.itemList.filter{
                            task -> task.title.contains(filtersState.searchText)
                    })
                    _uiState.value = HomeUiState(_uiState.value.itemList.filter{
                            task -> filtersState.categories.contains(task.category)
                    })
                    if(filtersState.onlyDoneTasksVisible) {
                        _uiState.value = HomeUiState(_uiState.value.itemList.filter{
                                task -> !task.isDone
                        })
                    }
                }
        }

    }

    fun rescheduleTasks (delay: Delay) {
        workManager.cancelAllWork()
        delayState = delay

        println("HALOOOOOOOOOOOOOOOOOOO -> $delayState")

        viewModelScope.launch {
            tasksRepository.getAllTasksStream().collect{ tasks ->
                tasks.forEach { task ->
                    val id = scheduleReminder(task, delay.value)
                    tasksRepository.updateTask(task.copy(notificationId = id))
                }
            }
        }

    }

    private fun scheduleReminder(
        task: Task, delay: Long
    ): UUID? {
        // create a Data instance with the task title passed to it
        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
        myWorkRequestBuilder.setInputData(
            workDataOf(
                "NAME" to task.title
            )
        )

        val duration = task.dueDateTime.timeInMillis -  System.currentTimeMillis() - delay

        if(duration > 0) {
            myWorkRequestBuilder.setInitialDelay(duration, TimeUnit.MILLISECONDS)
            val work = myWorkRequestBuilder.build()

            workManager.enqueue(work)

            return work.id
        }

        return null;
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Task> = listOf())
data class FiltersState(
    val searchText: String = "",
    val categories: List<String> = listOf(Category.School.name,Category.Other.name, Category.Office.name, Category.Home.name ),
    val onlyDoneTasksVisible: Boolean = false
)