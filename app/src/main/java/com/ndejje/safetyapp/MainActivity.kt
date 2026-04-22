package com.ndejje.safetyapp

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
import com.ndejje.safetyapp.R
import com.ndejje.safetyapp.AppDatabase
import com.ndejje.safetyapp.UserRepository
import com.ndejje.safetyapp.ui.theme.NdejjeSafetyAppTheme
import com.ndejje.safetyapp.AuthViewModel
import com.ndejje.safetyapp.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Build the dependency chain: DB → DAO → Repository → ViewModel
        val database   = AppDatabase.getInstance(applicationContext)
        val repository = UserRepository(database.userDao())
        val incidentRepository = IncidentRepository(database.incidentDao())
        val authViewModel: AuthViewModel by viewModels {
            AuthViewModelFactory(repository)
        }
        val safetyViewModel: SafetyViewModel by viewModels {
            SafetyViewModelFactory(incidentRepository)
        }

        setContent {
            NdejjeSafetyAppTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(
                        authViewModel = authViewModel,
                        safetyViewModel = safetyViewModel
                    )
                }
            }
        }
    }
}

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