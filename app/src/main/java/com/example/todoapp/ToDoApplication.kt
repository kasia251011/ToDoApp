package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.AppContainer
import com.example.todoapp.data.AppDataContainer

class ToDoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}