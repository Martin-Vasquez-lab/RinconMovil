package com.example.rinconinalambricomovil.ui.screens
import androidx.compose.runtime.Composable


import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rinconinalambricomovil.components.LoginForm
import com.example.rinconinalambricomovil.components.LoginViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import com.example.rinconinalambricomovil.ui.navigation.Routes


@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val loginState = viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Función para mostrar errores
    fun mostrarError(mensaje: String) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = "Error: $mensaje, vuelva a intentarlo",
                actionLabel = "Reintentar"
            )
        }
    }

    // Manejar estados del login
    LaunchedEffect(loginState.value) {
        when (val state = loginState.value) {
            is LoginViewModel.LoginState.Success -> {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is LoginViewModel.LoginState.Error -> {
                mostrarError(state.message)
            }
            else -> {}
        }
    }

    LoginForm(
        onLoginClick = { email, password ->
            // Validación simple
            if (email.isBlank() || password.isBlank()) {
                mostrarError("Email y contraseña son requeridos")
                return@LoginForm
            }
            viewModel.login(email, password)
        },
        onRegisterClick = {
            navController.navigate("register")
        },
        isLoading = loginState.value is LoginViewModel.LoginState.Loading
    )
}