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

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(
    username: String,
    onLogout: () -> Unit,
    onNavigateToReport: () -> Unit,
    onNavigateToAlerts: () -> Unit
) {
    val context = LocalContext.current // Used to launch the Dialer

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

        // 1. Report Incident
        Button(
            onClick = onNavigateToReport,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight))
        ) {
            Text(stringResource(R.string.btn_report_incident))
        }

        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        // 2. View Alerts
        ElevatedButton(
            onClick = onNavigateToAlerts,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight))
        ) {
            Text(stringResource(R.string.btn_view_alerts))
        }

        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        // 3. EMERGENCY CALL BUTTON
        // Using ButtonDefaults.buttonColors to make it Red (Error color)
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:+256700000000") // Replace with actual Ndejje Security number
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.buttonHeight)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(stringResource(R.string.btn_emergency_call))
        }

        Spacer(Modifier.height(48.dp))

        // 4. Logout
        TextButton(onClick = onLogout) {
            Text(
                text = stringResource(R.string.btn_logout),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}