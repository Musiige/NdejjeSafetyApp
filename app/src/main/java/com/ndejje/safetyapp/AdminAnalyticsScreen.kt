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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAnalyticsScreen(viewModel: SafetyViewModel, onBack: () -> Unit) {
    val incidentList by viewModel.incidents.collectAsState()
    val totalIncidents = incidentList.size

    // Consistent Admin Theme Color
    val adminNavy = Color(0xFF263238)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = adminNavy // Background for the entire screen
    ) {
        Scaffold(
            containerColor = Color.Transparent, // Let Surface handle background
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "SYSTEM ANALYTICS",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = adminNavy
                    )
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
                        // primaryContainer provides a nice contrast against the deep Navy
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                            text = "$totalIncidents",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Additional Breakdown Section
                Text(
                    text = "Campus Breakdown",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.Start).padding(start = 8.dp, bottom = 8.dp)
                )

                AnalyticsDetailRow("Main Campus", incidentList.count { it.campus == "Main Campus" })
                Divider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 8.dp))
                AnalyticsDetailRow("Kampala Campus", incidentList.count { it.campus == "Kampala Campus" })
            }
        }
    }
}

@Composable
fun AnalyticsDetailRow(label: String, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = "$count",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary // Use green for the numbers
        )
    }
}