package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.AppContainer
import com.example.todoapp.data.AppDataContainer
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class ToDoApplication : Application() {
    lateinit var container: AppContainer
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val name = "reminder_channel"
        val descriptionText = "reminder_reminder"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        container = AppDataContainer(this)
    }
    companion object {
        const val CHANNEL_ID = "reminder_id"
    }
}