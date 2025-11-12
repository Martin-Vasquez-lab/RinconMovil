package com.example.rinconinalambricomovil.ui.screens
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.rinconinalambricomovil.components.LoginViewModel  // ← ESTA LÍNEA
import  com.example.rinconinalambricomovil.components.LoginForm
// Si usas Compose Navigation



// screens/Login.kt
@Composable
fun LoginScreen(
    navController: NavController
) {
    val viewModel: LoginViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val loginState by viewModel.loginState.collectAsState()
    val isLoading = loginState is LoginViewModel.LoginState.Loading

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        LoginForm(
            onLoginClick = { email, password ->  // ← RECIBIR AMBOS PARÁMETROS
                viewModel.login(email, password)  // ← PASAR AMBOS AL VIEWMODEL
            },
            onRegisterClick = {
                navController.navigate("register")
            },
            isLoading = isLoading,
            modifier = Modifier.padding(paddingValues)
        )
    }

    // Manejar estados
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginViewModel.LoginState.Error -> {
                val errorMessage = (loginState as LoginViewModel.LoginState.Error).message
                snackbarHostState.showSnackbar(errorMessage)
                delay(3000)
                viewModel.resetState()
            }
            is LoginViewModel.LoginState.Success -> {
                navController.navigate("menu") {
                    popUpTo("login") { inclusive = true }
                }
            }
            else -> {}
        }
    }
}


