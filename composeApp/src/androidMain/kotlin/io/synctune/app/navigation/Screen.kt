package io.synctune.app.navigation

sealed class Screen (val route: String){
    object LogIn: Screen("login_screen")
    object SignUp: Screen("signup_screen")
}