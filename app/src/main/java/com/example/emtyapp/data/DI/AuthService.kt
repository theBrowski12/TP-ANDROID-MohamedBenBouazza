package com.example.emtyapp.data.DI

import com.example.emtyapp.data.Entities.User
import com.example.emtyapp.data.network.LoginCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body user: User): Response<User>

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginCredentials): Response<User>
}

