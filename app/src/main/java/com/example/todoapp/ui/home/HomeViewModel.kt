package com.example.todoapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Category
import com.example.todoapp.data.Task
import com.example.todoapp.data.TasksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    var filtersState by mutableStateOf(FiltersState())
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

        filtersState = FiltersState(
            searchText ?: filtersState.searchText,
            categories ?: filtersState.categories,
            doneTasksVisible ?: filtersState.onlyDoneTasksVisible)

        viewModelScope.launch {
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