
package com.ndejje.safetyapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val campus: String, // Main, Bombo, or Kampala
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending",

    val category: String,             // e.g., "Theft", "Emergency"
    val imagePath: String? = null,    // Stores the URI of the photo
    val isAnonymous: Boolean = false, // Toggle for hiding name

    val reporterName: String         // Always keep the real name for Admins
)