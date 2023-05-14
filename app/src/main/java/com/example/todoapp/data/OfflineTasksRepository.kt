package com.example.todoapp.data

import com.example.todoapp.data.Task
import com.example.todoapp.data.TaskDao
import com.example.todoapp.data.TasksRepository
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getAllNotFinishedTasksStream(): Flow<List<Task>> = taskDao.getAllNotFinishedTasks()

    override fun getTaskStream(id: Int): Flow<Task?> = taskDao.getTask(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)
}