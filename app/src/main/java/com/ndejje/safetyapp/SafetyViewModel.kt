package com.ndejje.safetyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SafetyViewModel(private val repository: IncidentRepository) : ViewModel() {

    // Converts the Flow from Room into a StateFlow for Compose
    // This satisfies the "Robust State Management" requirement
    val incidents: StateFlow<List<IncidentEntity>> = repository.allIncidents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun submitReport(title: String, description: String, campus: String) {
        viewModelScope.launch {
            val newIncident = IncidentEntity(
                title = title,
                description = description,
                campus = campus
            )
            repository.reportIncident(newIncident)
        }
    }
}