package com.ndejje.safetyapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val LOGIN = "login"
    const val REPORT = "report_incident"
    const val REGISTER = "register"
    const val ALERTS = "alerts"
    const val ANALYTICS = "analytics"
    const val HOME = "home"
    const val RESOURCES = "resources"
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    safetyViewModel: SafetyViewModel
) {
    val navController = rememberNavController()

    // Determine user details
    val currentUsername = safetyViewModel.loggedInUser ?: "Student"

    // Logic: If username contains 'admin', treat as Admin, else Student
    val currentUserRole = if (currentUsername.contains("admin", ignoreCase = true)) {
        "admin"
    } else {
        "student"
    }

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        // LOGIN SCREEN
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { username ->
                    safetyViewModel.login(username)
                    navController.navigate(Routes.HOME) {
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
                viewModel = authViewModel,
                onRegisterSuccess = { username ->
                    safetyViewModel.login(username)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // HOME SCREEN
        composable(Routes.HOME) {
            HomeScreen(
                username = currentUsername,
                userRole = currentUserRole,
                onLogout = {
                    safetyViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onNavigateToReport = { navController.navigate(Routes.REPORT) },
                onNavigateToAlerts = { navController.navigate(Routes.ALERTS) },
                onNavigateToAnalytics = { navController.navigate(Routes.ANALYTICS) },
                onNavigateToResources = { navController.navigate(Routes.RESOURCES) }
            )
        }

        // ALERTS SCREEN
        composable(Routes.ALERTS) {
            AlertsDashboard(
                viewModel = safetyViewModel,
                userRole = currentUserRole,
                currentUsername = currentUsername,
                onBack = { navController.popBackStack() }
            )
        }

        // REPORT SCREEN
        composable(Routes.REPORT) {
            ReportIncidentScreen(
                viewModel = safetyViewModel,
                onReportSubmitted = {
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ANALYTICS SCREEN
        composable(Routes.ANALYTICS) {
            AdminAnalyticsScreen(
                viewModel = safetyViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // RESOURCES SCREEN
        composable(Routes.RESOURCES) {
            SafetyResourcesScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}