package com.example.emtyapp.data.Entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("role")
    val role: String = "guest" // Default role
)