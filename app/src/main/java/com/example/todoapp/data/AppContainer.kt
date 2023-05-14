package com.example.todoapp.data
import android.content.Context

interface AppContainer {
    val tasksRepository: TasksRepository
}

class AppDataContainer(private val context: Context) : AppContainer  {
    override val tasksRepository: TasksRepository by lazy {
        OfflineItemsRepository(ToDoDatabase.getDatabase(context).taskDao())
    }
}