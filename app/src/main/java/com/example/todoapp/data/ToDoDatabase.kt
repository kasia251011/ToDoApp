package com.example.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase :  RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var Instance: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ToDoDatabase::class.java, "toDo_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}