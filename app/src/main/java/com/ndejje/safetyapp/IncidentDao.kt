package com.ndejje.safetyapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {
    @Insert
    suspend fun insertIncident(incident: IncidentEntity)

    // This Flow will automatically update the UI whenever a new incident is added
    // Satisfies the "Real-time updates/Dynamic list" requirement
    @Query("SELECT * FROM incidents ORDER BY timestamp DESC")
    fun getAllIncidents(): Flow<List<IncidentEntity>>

    @Query("SELECT * FROM incidents WHERE campus = :campusName")
    fun getIncidentsByCampus(campusName: String): Flow<List<IncidentEntity>>
}