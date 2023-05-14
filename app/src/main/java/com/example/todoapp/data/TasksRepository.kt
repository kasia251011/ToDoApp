package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllNotFinishedTasksStream(): Flow<List<Task>>

    fun getTaskStream(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)
}