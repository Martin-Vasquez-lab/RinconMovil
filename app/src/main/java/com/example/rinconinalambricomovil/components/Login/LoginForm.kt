package com.example.rinconinalambricomovil.components.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay

@Composable
fun LoginForm(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Validaciones
    val isEmailValid = email.isNotEmpty() && email.contains("@")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ✅ PRIMERO: Campo de EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            isError = email.isNotEmpty() && !isEmailValid,
            trailingIcon = {
                if (email.isNotEmpty()) {
                    var showText by remember { mutableStateOf(false) }

                    Box {
                        Icon(
                            imageVector = if (isEmailValid) Icons.Default.Check else Icons.Default.Error,
                            contentDescription = "Validación de email",
                            tint = if (isEmailValid) Color(0xFF4CAF50) else Color(0xFFF44336),
                            modifier = Modifier.clickable { showText = true }
                        )

                        if (showText) {
                            // Desaparece automáticamente después de 2 segundos
                            LaunchedEffect(showText) {
                                delay(2000)
                                showText = false
                            }

                            Text(
                                text = if (isEmailValid) "✓" else "Requiere formato email",
                                color = if (isEmailValid) Color.Green else Color.Red,
                                modifier = Modifier
                                    .offset(y = (-30).dp)
                                    .background(Color.White, RoundedCornerShape(4.dp))
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ DESPUÉS: Campo de CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Ocultar contraseña"
                        else "Mostrar contraseña",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de login
        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty() && isEmailValid
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro
        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}