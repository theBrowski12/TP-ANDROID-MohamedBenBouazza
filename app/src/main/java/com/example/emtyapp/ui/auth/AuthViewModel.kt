package com.example.emtyapp.ui.auth

import android.content.Context
import com.example.emtyapp.data.network.LoginCredentials
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.User
import com.example.emtyapp.data.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        userRepository.loginUser(email, password)
            .onSuccess { user ->
                _currentUser.value = user
                _errorMessage.value = null
            }
            .onFailure { e ->
                _errorMessage.value = e.message ?: "Login failed"
            }
    }

    fun register(user: User) = viewModelScope.launch {
        userRepository.registerUser(user)
            .onSuccess { user ->
                _currentUser.value = user
                _errorMessage.value = null
            }
            .onFailure { e ->
                _errorMessage.value = e.message ?: "Registration failed"
            }
    }
    fun logout() = viewModelScope.launch {
        userRepository.logout()
        _currentUser.value = null
    }
}