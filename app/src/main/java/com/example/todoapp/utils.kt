package com.example.todoapp

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

fun capitalizeWords(text: String): String {
    return text.split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
}

fun fillWith0(number: Int): String {
    return if (number < 10) "0$number" else number.toString()
}

@SuppressLint("SimpleDateFormat")
fun calendarToString(calendar: Calendar): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm")
    return sdf.format(calendar.time)
}