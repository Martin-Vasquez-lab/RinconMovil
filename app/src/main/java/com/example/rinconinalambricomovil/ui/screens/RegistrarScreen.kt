package com.example.rinconinalambricomovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rinconinalambricomovil.components.Register.RegisterViewModel
import com.example.rinconinalambricomovil.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    // Navegar si el registro fue exitoso
    LaunchedEffect(viewModel.registroExitoso) {
        if (viewModel.registroExitoso) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.HOME) // Limpia el stack para no volver al registro
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Campo Nombre
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.updateNombre(it) },
                label = { Text("Nombre completo *") },
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.nombre.isEmpty() && viewModel.nombre.isNotEmpty()
            )

            // Campo Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth(),
                isError = viewModel.email.isNotEmpty() && !viewModel.emailValid
            )
            if (viewModel.email.isNotEmpty() && !viewModel.emailValid) {
                Text(
                    text = "Email inválido",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            // Campo Teléfono
            OutlinedTextField(
                value = viewModel.telefono,
                onValueChange = { viewModel.updateTelefono(it) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Contraseña *") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = viewModel.password.isNotEmpty() && viewModel.password.length < 6
            )
            if (viewModel.password.isNotEmpty() && viewModel.password.length < 6) {
                Text(
                    text = "Mínimo 6 caracteres",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = { Text("Confirmar contraseña *") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = viewModel.confirmPassword.isNotEmpty() && !viewModel.passwordsMatch
            )
            if (viewModel.confirmPassword.isNotEmpty() && !viewModel.passwordsMatch) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            // Checkbox Términos y Condiciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.aceptaTerminos,
                    onCheckedChange = { viewModel.updateAceptaTerminos(it) }
                )
                Text(
                    text = "Acepto los términos y condiciones *",
                    modifier = Modifier.padding(start = 8.dp),
                    color = if (!viewModel.aceptaTerminos && viewModel.aceptaTerminos) Color.Red else Color.Unspecified
                )
            }

            // Mensaje de error general
            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            // Botón de Registro
            Button(
                onClick = { viewModel.registrarUsuario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = viewModel.formularioValido && !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            // Enlace para ir a Login
            TextButton(onClick = { navController.navigate(Routes.LOGIN) }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }

            // Información de campos obligatorios
            Text(
                text = "* Campos obligatorios",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
