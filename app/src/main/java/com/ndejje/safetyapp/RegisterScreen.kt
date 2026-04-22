package com.ndejje.safetyapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ndejje.safetyapp.R
import com.ndejje.safetyapp.AuthUiState
import com.ndejje.safetyapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val authState        by viewModel.authState.collectAsState()
    var fullNameInput    by remember { mutableStateOf("") }
    var usernameInput    by remember { mutableStateOf("") }
    var emailInput       by remember { mutableStateOf("") }
    var passwordInput    by remember { mutableStateOf("") }
    var confirmPassInput by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState is AuthUiState.Success) {
            onRegisterSuccess((authState as AuthUiState.Success).username)
            viewModel.resetState()
        }
    }

    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(dimensionResource(R.dimen.screenPadding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(stringResource(R.string.label_register),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingLarge)))

        OutlinedTextField(value = fullNameInput,
            onValueChange = { fullNameInput = it },
            label = { Text(stringResource(R.string.label_full_name)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        OutlinedTextField(value = usernameInput,
            onValueChange = { usernameInput = it },
            label = { Text(stringResource(R.string.label_username)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        OutlinedTextField(value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(stringResource(R.string.label_email)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        OutlinedTextField(value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(stringResource(R.string.label_password)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))

        OutlinedTextField(value = confirmPassInput,
            onValueChange = { confirmPassInput = it },
            label = { Text(stringResource(R.string.label_confirm_password)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingSmall)))

        if (authState is AuthUiState.Error) {
            Text((authState as AuthUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(dimensionResource(R.dimen.spacingSmall)))
        }

        Button(onClick = {
            viewModel.register(fullNameInput, usernameInput,
                emailInput, passwordInput, confirmPassInput)
        }, modifier = Modifier.fillMaxWidth()
            .height(dimensionResource(R.dimen.buttonHeight)),
            enabled = authState !is AuthUiState.Loading) {
            if (authState is AuthUiState.Loading)
                CircularProgressIndicator(Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary)
            else Text(stringResource(R.string.btn_register))
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.spacingMedium)))
        TextButton(onClick = { viewModel.resetState(); onNavigateToLogin() }) {
            Text(stringResource(R.string.link_back_to_login))
        }
    }
}