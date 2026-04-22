package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ndejje.safetyapp.R

@Composable
fun HomeScreen(
    username: String,
    onLogout: () -> Unit,
    onNavigateToReport: () -> Unit, // Add this
    onNavigateToAlerts: () -> Unit  // Add this
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.screenPadding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.label_welcome, username),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        // 1. Button to Report Incident
        Button(
            onClick = onNavigateToReport,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight))
        ) {
            Text("REPORT AN INCIDENT") // We will move this to strings.xml later
        }

        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        // 2. Button to View Live Alerts
        ElevatedButton(
            onClick = onNavigateToAlerts,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight))
        ) {
            Text("VIEW LIVE ALERTS")
        }

        Spacer(Modifier.height(48.dp))

        // 3. Logout Button
        TextButton(onClick = onLogout) {
            Text(stringResource(R.string.btn_logout), color = MaterialTheme.colorScheme.error)
        }
    }
}