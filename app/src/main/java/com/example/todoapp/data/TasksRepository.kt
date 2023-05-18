package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasksStream(): Flow<List<Task>>

    fun getAllTasksByCategories(categories: Array<String?>): Flow<List<Task>>

    fun getAllTasksByTitle(title: String): Flow<List<Task>>

    fun getTaskStream(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)
}