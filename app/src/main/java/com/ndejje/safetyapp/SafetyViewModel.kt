package com.ndejje.safetyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class SafetyViewModel(private val repository: IncidentRepository) : ViewModel() {

    val incidents: StateFlow<List<IncidentEntity>> = repository.allIncidents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UPDATED: Now accepts the 6 arguments from your UI
    fun submitReport(
        title: String,
        description: String,
        campus: String,
        category: String,      // NEW
        imageUri: String?,     // NEW
        isAnonymous: Boolean   // NEW
    ) {
        viewModelScope.launch {
            val newIncident = IncidentEntity(
                title = title,
                description = description,
                campus = campus,
                category = category,           // Match Entity
                imagePath = imageUri,          // Match Entity
                isAnonymous = isAnonymous,     // Match Entity
                reporterName = "Student User"  // Placeholder - you can link this to your login state later
            )
            // Ensure this matches your Repository function name (usually 'insertIncident' or 'reportIncident')
            repository.reportIncident(newIncident)
        }
    }
}