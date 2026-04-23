package com.ndejje.safetyapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndejje.safetyapp.HomeScreen
import com.ndejje.safetyapp.LoginScreen
import com.ndejje.safetyapp.RegisterScreen
import com.ndejje.safetyapp.AuthViewModel

object Routes {
    const val LOGIN    = "login"
    const val REPORT = "report_incident"
    const val REGISTER = "register"
    // FIX: Change the literal email to a placeholder
    const val HOME     = "home/{username}"
}
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,    // Renamed from 'viewModel' to be specific
    safetyViewModel: SafetyViewModel // Added the new ViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        // LOGIN SCREEN
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = authViewModel, // Pass authViewModel here
                onLoginSuccess = { username ->
                    navController.navigate("home/$username") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    authViewModel.resetState()
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        // REGISTER SCREEN
        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel, // Pass authViewModel here
                onRegisterSuccess = { username ->
                    navController.navigate("home/$username") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // HOME SCREEN
        composable(
            route = Routes.HOME,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "User"
            HomeScreen(
                username = username,
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo("home/$username") { inclusive = true }
                    }
                },
                onNavigateToReport = { navController.navigate("report_incident") },
                onNavigateToAlerts = { navController.navigate("alerts") }
            )
        }

        // Inside NavHost in AppNavigation.kt
        composable(Routes.REPORT) {
            ReportIncidentScreen(
                viewModel = safetyViewModel,
                onReportSubmitted = {
                    // After submitting, go back to Home or Alerts
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("alerts") {
            // We will build AlertsDashboard soon
            Text("Alerts Dashboard Coming Soon")
        }
    }
}