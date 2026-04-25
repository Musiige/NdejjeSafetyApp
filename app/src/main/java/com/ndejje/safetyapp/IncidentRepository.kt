package com.ndejje.safetyapp

import kotlinx.coroutines.flow.Flow

class IncidentRepository(private val incidentDao: IncidentDao) {

    // Gets all incidents for the Live Alerts Dashboard (Dynamic List)
    val allIncidents: Flow<List<IncidentEntity>> = incidentDao.getAllIncidents()

    // Function to save a new report
    suspend fun reportIncident(incident: IncidentEntity) {
        incidentDao.insertIncident(incident)
    }

    suspend fun updateIncidentStatus(id: Int, status: String) {
        incidentDao.updateStatus(id, status)
    }

    // Function to filter by campus (Useful for the Dashboard)
    fun getIncidentsByCampus(campus: String): Flow<List<IncidentEntity>> =
        incidentDao.getIncidentsByCampus(campus)
}