package com.example.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    //TODO: Change sorting by dueDate
    @Query("SELECT * from task ORDER BY dueDateTime ASC")
    fun getAllNotFinishedTasks(): Flow<List<Task>>

    @Query("SELECT * from task WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}