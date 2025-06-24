package com.example.emtyapp.data.Repository

import com.example.emtyapp.data.Entities.User

// UserRepository.kt
interface UserRepository {
    suspend fun registerUser(user: User): Result<User>
    suspend fun loginUser(email: String, password: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
    suspend fun updateUser(user: User): Result<User>
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun deleteUserById(userId: String): Result<Unit>

}