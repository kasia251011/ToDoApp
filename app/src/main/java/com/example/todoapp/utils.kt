package com.example.todoapp

fun capitalizeWords(text: String): String {
    return text.split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
}
