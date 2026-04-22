
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
    val status: String = "Pending"
)