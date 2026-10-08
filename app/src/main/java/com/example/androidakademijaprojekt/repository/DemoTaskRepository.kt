
package com.example.androidakademijaprojekt.repository

import com.example.androidakademijaprojekt.database.TaskDatabase
import com.example.androidakademijaprojekt.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import com.example.androidakademijaprojekt.domain.Task
import com.example.androidakademijaprojekt.database.toDomainList
import com.example.androidakademijaprojekt.database.toDomain



class DemoTaskRepository(
    database: TaskDatabase
) {
    private val taskDao = database.taskDao()



    fun observeDomainTasks(): Flow<List<Task>> {
        return taskDao.observeTasks().map { entities ->
            entities.toDomainList()
        }
    }


    suspend fun createTask(title: String, body: String) {
        val task = TaskEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            body = body,
            createdAt = todayDate(),
            isSynced = false
        )

        taskDao.insertTask(task)
    }

    suspend fun updateTask(
        taskId: String,
        title: String,
        body: String
    ) {
        val existing = taskDao.getTaskById(taskId)
            ?: throw IllegalArgumentException("Task not found.")

        taskDao.insertTask(
            existing.copy(
                title = title,
                body = body,
                isSynced = false
            )
        )
    }

    suspend fun deleteTask(taskId: String) {
        taskDao.deleteTaskById(taskId)
    }


    suspend fun getDomainTaskById(taskId: String): Task? {
        return taskDao.getTaskById(taskId)?.toDomain()
    }

    private fun todayDate(): String {
        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())
    }
}
