package com.ndejje.safetyapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val campus: String,
    val imagePath: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val category: String,
    val isAnonymous: Boolean = false,
    val reporterName: String,
    val status: String = "Approved" // "Approved" or "Pending"
)