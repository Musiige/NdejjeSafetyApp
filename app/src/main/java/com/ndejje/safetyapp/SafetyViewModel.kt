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

    // UPDATED: Now takes 'context' to trigger the notification
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
            val newIncident = IncidentEntity(
                title = title,
                description = description,
                campus = campus,
                category = category,
                imagePath = imageUri,
                isAnonymous = isAnonymous,
                reporterName = loggedInUser ?: "Guest User"
            )
            repository.reportIncident(newIncident)

            // Trigger the notification after saving to DB
            showLocalNotification(context, title)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showLocalNotification(context: Context, reportTitle: String) {
        val builder = NotificationCompat.Builder(context, "SAFETY_CHANNEL")
            .setSmallIcon(R.drawable.ic_notification) // The white vector icon you created
            .setContentTitle("Report Submitted")
            .setContentText("Incident: $reportTitle has been recorded.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // ID 1 is fine for a single notification
            notify(1, builder.build())
        }
    }
}