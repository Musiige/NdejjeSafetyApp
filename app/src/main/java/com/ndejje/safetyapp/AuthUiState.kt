package com.ndejje.safetyapp

sealed class AuthUiState {
    object Idle    : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val username: String) : AuthUiState()
    data class Error(val message: String)   : AuthUiState()
}