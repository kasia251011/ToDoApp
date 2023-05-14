package com.example.todoapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.ToDoApplication
import com.example.todoapp.ui.home.HomeViewModel
import com.example.todoapp.ui.task.add.AddTaskViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            AddTaskViewModel(
                toDoApplication().container.tasksRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(toDoApplication().container.tasksRepository)
        }
    }
}

fun CreationExtras.toDoApplication(): ToDoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication)