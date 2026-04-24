package com.ndejje.safetyapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat // NEW: For permissions
import com.ndejje.safetyapp.ui.theme.NdejjeSafetyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. CREATE NOTIFICATION CHANNEL (Required for Android 8.0+)
        createNotificationChannel()

        // 2. REQUEST PERMISSION (Required for Android 13+)
        // This won't lag your PC; it's a simple OS check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }

        // Build the dependency chain
        val database = AppDatabase.getInstance(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val incidentRepo = IncidentRepository(database.incidentDao())

        val authViewModel: AuthViewModel by viewModels {
            AuthViewModelFactory(userRepository)
        }
        val safetyViewModel: SafetyViewModel by viewModels {
            SafetyViewModelFactory(incidentRepo)
        }

        setContent {
            NdejjeSafetyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(
                        authViewModel = authViewModel,
                        safetyViewModel = safetyViewModel
                    )
                }
            }
        }
    }

    // 3. CHANNEL LOGIC (Separated to keep onCreate clean)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Safety Alerts"
            val descriptionText = "Notifications for Ndejje Safety Reports"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("SAFETY_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

// ... Keep your Preview code exactly as it was below this ...

// ─────────────────────────────────────────────────────────────
// PREVIEW
// @Preview cannot instantiate a real ViewModel — the preview
// shows the Login screen's visual layout using static values.
// stringResource() and dimensionResource() resolve correctly
// inside @Preview — always use them to stay consistent with
// the coding standards applied throughout the rest of the app.
// ─────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Login Screen Preview")
@Composable
fun LoginScreenPreview() {
    NdejjeSafetyAppTheme() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.screenPadding)),
                verticalArrangement   = Arrangement.Center,
                horizontalAlignment   = Alignment.CenterHorizontally
            ) {
                Text(
                    text      = stringResource(R.string.label_login),
                    style     = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingLarge)))
                OutlinedTextField(
                    value         = "",
                    onValueChange = {},
                    label         = { Text(stringResource(R.string.label_username)) },
                    modifier      = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingMedium)))
                OutlinedTextField(
                    value         = "",
                    onValueChange = {},
                    label         = { Text(stringResource(R.string.label_password)) },
                    modifier      = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingMedium)))
                Button(
                    onClick  = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.buttonHeight))
                ) {
                    Text(stringResource(R.string.btn_login))
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacingSmall)))
                TextButton(onClick = {}) {
                    Text(stringResource(R.string.link_register))
                }
            }
        }
    }
}