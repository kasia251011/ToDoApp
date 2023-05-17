package com.example.todoapp

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

fun capitalizeWords(text: String): String {
    return text.split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
}

@SuppressLint("SimpleDateFormat")
fun calendarToString(calendar: Calendar): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm")
    return sdf.format(calendar.time)
}