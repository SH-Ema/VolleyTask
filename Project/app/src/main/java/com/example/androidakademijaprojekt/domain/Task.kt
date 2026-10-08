package com.example.androidakademijaprojekt.domain

data class Task(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String? = null
)
