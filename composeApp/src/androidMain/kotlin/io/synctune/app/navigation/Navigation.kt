package io.synctune.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.synctune.app.screens.LoginScreen
import io.synctune.app.screens.SignupScreen
import io.synctune.app.viewmodels.AuthViewModel

@Composable
fun Navigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.LogIn.route) {

        composable(Screen.LogIn.route) {
            LoginScreen(
                authViewModel = authViewModel,
                navigateToSignup = { navController.navigate(Screen.SignUp.route) }
            ) {

            }
        }

        composable(Screen.SignUp.route) {
            SignupScreen(
                authViewModel = authViewModel
            ) {
                navController.navigate(Screen.LogIn.route)
            }
        }
    }
}