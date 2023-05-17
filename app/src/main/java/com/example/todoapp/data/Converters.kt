package com.example.todoapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = value ?: 0;
        return calendar
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun dateToTimestamp(date: Calendar?): Long? {
        return date?.timeInMillis
    }
}