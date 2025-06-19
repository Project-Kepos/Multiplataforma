package org.example.kepos

import AddGreenhouseScreen
import GreenhouseScreen
import SignInScreen
import SignUpScreen
import org.example.kepos.ui.screens.MyAccountScreen

import androidx.compose.material.*
import androidx.compose.runtime.*
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.screens.*

sealed class Screen {
    object SignIn : Screen()
    object SignUp : Screen()
    object Home : Screen()
    object AddGreenhouse : Screen()
    data class Greenhouse(val id: String) : Screen()
    object MyAccount : Screen()
}


@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.SignIn) }

    MaterialTheme {
        when (val screen = currentScreen) {
            is Screen.SignIn -> SignInScreen(
                onSignInSuccess = { currentScreen = Screen.Home },
                onNavigateToSignUp = { currentScreen = Screen.SignUp }
            )
            is Screen.SignUp -> SignUpScreen(
                onSignUpSuccess = { currentScreen = Screen.Home },
                onNavigateToSignIn = { currentScreen = Screen.SignIn }
            )
            is Screen.Home -> HomeScreen(
                onNavigateToAddGreenhouse = { currentScreen = Screen.AddGreenhouse },
                onNavigateToGreenhouse = { dendroId -> currentScreen = Screen.Greenhouse(dendroId) },
                onNavigateToAccount = { currentScreen = Screen.MyAccount },
                onLogout = { currentScreen = Screen.SignIn }
            )
            is Screen.AddGreenhouse -> AddGreenhouseScreen(
                onBack = { currentScreen = Screen.Home }
            )
            is Screen.Greenhouse -> GreenhouseScreen(
                id = screen.id,
                onBack = { currentScreen = Screen.Home }
            )
            is Screen.MyAccount -> MyAccountScreen(
                onBack = { currentScreen = Screen.Home },
                onLogout = { currentScreen = Screen.SignIn }
            )
        }
    }
}
