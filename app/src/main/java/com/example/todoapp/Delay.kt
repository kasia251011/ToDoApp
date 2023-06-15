package com.example.todoapp

enum class Delay(val label: String,val value: Long) {
    MIN1("1 min", 60000L),
    MIN3("3 min", 180000L),
    MIN5("5 min", 300000L),
}
