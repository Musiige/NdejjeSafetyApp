package com.ndejje.safetyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SafetyViewModelFactory(
    private val repository: IncidentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SafetyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SafetyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}