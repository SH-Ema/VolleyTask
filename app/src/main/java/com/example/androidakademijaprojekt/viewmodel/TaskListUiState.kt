
package com.example.androidakademijaprojekt.viewmodel

import com.example.androidakademijaprojekt.domain.Task

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
