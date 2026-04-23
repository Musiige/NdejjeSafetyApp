package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAnalyticsScreen(viewModel: SafetyViewModel, onBack: () -> Unit) {
    // 1. Observe the list from the ViewModel
    val incidentList by viewModel.incidents.collectAsState()

    // 2. Get the size
    val totalIncidents = incidentList.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("System Analytics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Big Stat Card for Total Reports
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Reports Received",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$totalIncidents", // This is your incidents.size
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Additional Breakdown
            AnalyticsDetailRow("Main Campus", incidentList.count { it.campus == "Main Campus" })
            AnalyticsDetailRow("Bombo Campus", incidentList.count { it.campus == "Bombo Campus" })
            AnalyticsDetailRow("Kampala Campus", incidentList.count { it.campus == "Kampala Campus" })
        }
    }
}

@Composable
fun AnalyticsDetailRow(label: String, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text("$count", fontWeight = FontWeight.Bold)
    }
}