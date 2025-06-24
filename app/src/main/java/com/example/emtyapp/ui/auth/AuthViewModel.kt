package com.example.emtyapp.ui.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.User
import com.example.emtyapp.data.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

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
    fun updateProfile(name: String, email: String, role: String) {
        // TODO: appeler ton repository / API pour mettre à jour le profil
        // Puis mettre à jour currentUser via un StateFlow ou LiveData

        val updatedUser = currentUser.value?.copy(
            name = name,
            email = email,
            role = role
        )
        _currentUser.value = updatedUser // Exemple avec MutableStateFlow _currentUser
    }
    fun clearError() {
        _errorMessage.value = null
    }

    fun loadAllUsersIfAdmin() = viewModelScope.launch {
        val current = currentUser.value
        if (current?.role?.lowercase() == "admin") {
            userRepository.getAllUsers().onSuccess { users ->
                _allUsers.value = users
            }.onFailure {
                _errorMessage.value = "Failed to load users"
            }
        } else {
            _errorMessage.value = "Access denied. Admin only."
        }
    }
    fun deleteUser(userId: String) {
        viewModelScope.launch {
            try {
                userRepository.deleteUserById(userId)  // Méthode pour supprimer user en base
                loadAllUsersIfAdmin() // Recharge la liste
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la suppression"
            }
        }
    }
    fun updateUserRole(userId: String, newRole: String) {
        viewModelScope.launch {
            val user = allUsers.value.find { it.id == userId }
            if (user != null) {
                val updatedUser = user.copy(role = newRole)
                userRepository.updateUser(updatedUser)
                    .onSuccess {
                        loadAllUsersIfAdmin()  // rafraîchir la liste
                    }
                    .onFailure {
                        _errorMessage.value = "Erreur lors de la mise à jour du rôle"
                    }
            }
        }
    }



}