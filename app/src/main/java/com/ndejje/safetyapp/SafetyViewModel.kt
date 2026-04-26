package com.ndejje.safetyapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SafetyViewModel(private val repository: IncidentRepository) : ViewModel() {

    var loggedInUser by mutableStateOf<String?>(null)
        private set

    // The Dashboard will observe this list
    val incidents: StateFlow<List<IncidentEntity>> = repository.allIncidents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun login(username: String) {
        loggedInUser = username
    }

    fun logout() {
        loggedInUser = null
    }

    /**
     * ADMIN LOGIC: Update a report's status to "Approved"
     * This will make "Pending" reports visible to everyone.
     */
    fun approveIncident(incidentId: Int) {
        viewModelScope.launch {
            repository.updateIncidentStatus(incidentId, "Approved")
        }
    }

    /**
     * Submits a report to the Room Database.
     */
    fun submitReport(
        context: Context,
        title: String,
        description: String,
        campus: String,
        category: String,
        imageUri: String?,
        isAnonymous: Boolean
    ) {
        viewModelScope.launch {
            // Determine initial visibility/status
            val initialStatus = if (category == "Sexual Harassment") {
                "Pending"
            } else {
                "Approved"
            }

            val newIncident = IncidentEntity(
                title = title,
                description = description,
                campus = campus,
                category = category,
                imagePath = imageUri,
                isAnonymous = isAnonymous,
                reporterName = loggedInUser ?: "Guest User",
                status = initialStatus
            )

            repository.reportIncident(newIncident)
            showLocalNotification(context, title)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showLocalNotification(context: Context, reportTitle: String) {
        val builder = NotificationCompat.Builder(context, "SAFETY_CHANNEL")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Report Submitted")
            .setContentText("Incident: $reportTitle has been recorded.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}