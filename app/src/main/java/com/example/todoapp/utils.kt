package com.example.todoapp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    val sdf = SimpleDateFormat("dd/MM/yy HH:mm")
    return sdf.format(calendar.time)
}

fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap? {
    return if (imageData.isNotEmpty()) {
        BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    } else {
        null
    }
}