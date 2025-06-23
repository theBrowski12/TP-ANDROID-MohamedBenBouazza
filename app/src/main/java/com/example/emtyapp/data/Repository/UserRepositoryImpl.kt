package com.example.emtyapp.data.Repository

import android.content.Context
import android.util.Log
import com.example.emtyapp.data.Entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.UUID
import javax.inject.Inject

// UserRepositoryImpl.kt
class UserRepositoryImpl @Inject constructor(
    private val context: Context
) : UserRepository {
    private val usersFile = "users.json"
    private val currentUserFile = "current_user.json"
    private val gson = Gson()

    // In UserRepositoryImpl.kt
    init {
        // Create empty files if they don't exist
        val usersFile = File(context.filesDir, "users.json")
        if (!usersFile.exists()) {
            usersFile.writeText("[]")
        }

        val currentUserFile = File(context.filesDir, "current_user.json")
        if (!currentUserFile.exists()) {
            currentUserFile.writeText("")
        }
        if (getUsers().isEmpty()) {
            saveUsers(listOf(
                User("U00","admin@example.com", "admin123", "simo", "1245", "admin"),
                User("U001","test@example.com", "test123", "karim", "123444", "user")
            ))
        }
    }
    override suspend fun registerUser(user: User): Result<User> {
        return try {
            val users = getUsers().toMutableList()
            if (users.any { it.email == user.email }) {
                Result.failure(Exception("Email already exists"))
            } else {
                val newUser = user.copy(id = UUID.randomUUID().toString())
                users.add(newUser)
                saveUsers(users)
                saveCurrentUser(newUser)
                Result.success(newUser)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Registration failed", e)
            Result.failure(Exception("Registration failed: ${e.message}"))
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = getUsers().firstOrNull {
                it.email.equals(email, ignoreCase = true) && it.password == password
            } ?: return Result.failure(Exception("Invalid credentials"))
            Result.success(user)
        } catch (e: Exception) {
            Log.e("UserRepository", "Login failed", e)
            Result.failure(Exception("Login failed: ${e.message}"))
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            val file = File(context.filesDir, currentUserFile)
            if (file.exists()) {
                gson.fromJson(file.readText(), User::class.java)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun logout() {
        File(context.filesDir, currentUserFile).delete()
    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val users = getUsers().toMutableList()
            val index = users.indexOfFirst { it.id == user.id }
            if (index != -1) {
                users[index] = user
                saveUsers(users)
                saveCurrentUser(user)
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // In UserRepositoryImpl.kt
    private fun getUsers(): List<User> {
        return try {
            val file = File(context.filesDir, usersFile)
            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]")
                emptyList()
            } else {
                val json = file.readText()
                if (json.isBlank()) emptyList()
                else gson.fromJson(json, object : TypeToken<List<User>>() {}.type) ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error reading users", e)
            emptyList()
        }
    }

    // In UserRepositoryImpl
    private fun saveUsers(users: List<User>) {
        try {
            val file = File(context.filesDir, "users.json")
            file.writeText(gson.toJson(users))
        } catch (e: Exception) {
            Log.e("UserRepository", "Error saving users", e)
        }
    }

    private fun saveCurrentUser(user: User) {
        try {
            val file = File(context.filesDir, currentUserFile)
            file.writeText(gson.toJson(user))
            Log.d("UserRepository", "Saved current user: ${user.email}")
        } catch (e: Exception) {
            Log.e("UserRepository", "Error saving current user", e)
        }
    }

}