package com.ndejje.safetyapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SafetyViewModel(private val repository: IncidentRepository) : ViewModel() {

    // 1. TRACK LOGGED IN USER
    // We use 'null' to mean no one is logged in.
    var loggedInUser by mutableStateOf<String?>(null)
        private set

    // 2. INCIDENTS LIST
    val incidents: StateFlow<List<IncidentEntity>> = repository.allIncidents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 3. LOGIN LOGIC
    fun login(username: String) {
        loggedInUser = username
    }

    // 4. LOGOUT LOGIC
    fun logout() {
        loggedInUser = null
    }

    // 5. SUBMIT REPORT
    fun submitReport(
        title: String,
        description: String,
        campus: String,
        category: String,
        imageUri: String?,
        isAnonymous: Boolean
    ) {
        viewModelScope.launch {
            val newIncident = IncidentEntity(
                title = title,
                description = description,
                campus = campus,
                category = category,
                imagePath = imageUri,
                isAnonymous = isAnonymous,
                // Use the real logged-in name, or 'Guest' if null
                reporterName = loggedInUser ?: "Guest User"
            )
            repository.reportIncident(newIncident)
        }
    }
}