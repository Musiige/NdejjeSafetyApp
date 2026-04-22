package com.ndejje.safetyapp

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
    const val REGISTER = "register"
    // FIX: Change the literal email to a placeholder
    const val HOME     = "home/{username}"
}
@Composable
fun AppNavigation(viewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { username ->
                    navController.navigate("home/$username") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    viewModel.resetState()
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = { username ->
                    navController.navigate("home/$username") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.HOME, // This is now "home/{username}"
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "User"
            HomeScreen(username = username, onLogout = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo("home/{username}") { inclusive = true } // Use the pattern here too
                }
            })
        }
    }
}