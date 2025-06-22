package com.example.emtyapp.ui.auth

import com.example.emtyapp.data.network.LoginCredentials
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.DI.AuthService
import com.example.emtyapp.data.Entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    fun register(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authService.register(user)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(response.message())
                }
            } catch (e: Exception) {
                onError(e.message ?: "Registration failed")
            }
        }
    }

    fun login(email: String, password: String, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authService.login(LoginCredentials(email, password))
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(response.message())
                }
            } catch (e: Exception) {
                onError(e.message ?: "Login failed")
            }
        }
    }
}