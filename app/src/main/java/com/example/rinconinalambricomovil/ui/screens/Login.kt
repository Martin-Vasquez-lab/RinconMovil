package com.example.rinconinalambricomovil.ui.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rinconinalambricomovil.components.Login.LoginForm
import com.example.rinconinalambricomovil.components.Login.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.rinconinalambricomovil.ui.state.UserSessionViewModel
import com.example.rinconinalambricomovil.ui.navigation.Routes



// Helper function to find the activity
private fun Context.findActivity(): FragmentActivity? = when (this) {
    is FragmentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

// screens/Login.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    sessionViewModel: UserSessionViewModel
) {
    val viewModel: LoginViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val loginState by viewModel.loginState.collectAsState()
    val isLoading = loginState is LoginViewModel.LoginState.Loading
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    //val sessionVM: UserSessionViewModel = viewModel()

    fun showBiometricPrompt(email: String, pass: String) {
        val activity = context.findActivity()
        if (activity == null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("No se pudo encontrar la actividad para la autenticación.")
            }
            return
        }

        val biometricManager = BiometricManager.from(activity)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS) {
            val executor = ContextCompat.getMainExecutor(activity)
            val biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        viewModel.login(email, pass)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON && errorCode != BiometricPrompt.ERROR_USER_CANCELED) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error de autenticación: $errString")
                            }
                        }
                    }
                }
            )

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación por huella dactilar")
                .setSubtitle("Inicia sesión con tu huella")
                .setNegativeButtonText("Cancelar")
                .build()
            biometricPrompt.authenticate(promptInfo)
        } else {
            viewModel.login(email, pass)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        LoginForm(
            onLoginClick = { email, password ->
                showBiometricPrompt(email, password)
            },
            onRegisterClick = {
                navController.navigate("registrar")
            },
            isLoading = isLoading,
            modifier = Modifier.padding(paddingValues)
        )
    }

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is LoginViewModel.LoginState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                delay(3000)
                viewModel.resetState()
            }
            is LoginViewModel.LoginState.Success -> {
                sessionViewModel.iniciarSesion(state.user)

                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            }
            else -> {}
        }
    }


}
