package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getAllTasksByCategories(categories: Array<String?>): Flow<List<Task>> =
        taskDao.getAllTasksByCategories(categories)

    override fun getAllTasksByTitle(title: String): Flow<List<Task>> =
        taskDao.getAllTasksByTitle(title)

    override fun getTaskStream(id: Int): Flow<Task?> = taskDao.getTask(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)
}