package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    onLogout: () -> Unit,
    onNavigateToReport: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToResources: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("NDEJJE SAFETY", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome, $username",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "What would you like to do today?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icons.Default.Add (The plus sign - perfect for 'Reporting')
                item { MenuCard("Report", Icons.Default.Add, onNavigateToReport) }

                // Icons.Default.Info (The 'i' symbol - great for 'Alerts')
                item { MenuCard("Alerts", Icons.Default.Info, onNavigateToAlerts) }

                // Icons.Default.Menu (Three lines - generic but safe for 'Analytics')
                item { MenuCard("Analytics", Icons.Default.Menu, onNavigateToAnalytics) }

                // Icons.Default.Home (The house icon - safe for 'Resources/Campus Info')
                item { MenuCard("Resources", Icons.Default.Home, onNavigateToResources) }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.height(120.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold)
        }
    }
}