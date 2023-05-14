package com.example.todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(tasksRepository: TasksRepository) : ViewModel() {

    /**
     * Holds home ui state. The list of items are retrieved from [TasksRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        tasksRepository.getAllNotFinishedTasksStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Task> = listOf())