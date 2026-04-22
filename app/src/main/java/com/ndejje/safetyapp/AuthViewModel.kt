package com.ndejje.safetyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndejje.safetyapp.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _authState.value = AuthUiState.Error("All fields are required")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            val user = repository.loginUser(username.trim(), password)
            _authState.value = if (user != null)
                AuthUiState.Success(user.username)
            else
                AuthUiState.Error("Invalid username or password")
        }
    }

    fun register(
        fullName: String, username: String,
        email: String,    password: String,
        confirmPassword: String
    ) {
        if (fullName.isBlank() || username.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthUiState.Error("All fields are required"); return
        }
        if (password != confirmPassword) {
            _authState.value = AuthUiState.Error("Passwords do not match"); return
        }
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            val success = repository.registerUser(
                fullName = fullName.trim(), username = username.trim(),
                email    = email.trim(),    password = password
            )
            _authState.value = if (success)
                AuthUiState.Success(username.trim())
            else
                AuthUiState.Error("Username already exists")
        }
    }

    fun resetState() { _authState.value = AuthUiState.Idle }
}