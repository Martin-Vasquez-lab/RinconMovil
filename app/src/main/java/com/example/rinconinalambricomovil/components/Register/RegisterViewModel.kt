package com.example.rinconinalambricomovil.components.Register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Patterns
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.data.Usuarios
import com.example.rinconinalambricomovil.model.User

class RegisterViewModel : ViewModel() {
    // Estado del formulario
    var nombre by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var telefono by mutableStateOf("")
        private set
    var aceptaTerminos by mutableStateOf(false)
        private set

    // Estado de la UI
    var isLoading by mutableStateOf(false)
        private set
    var registroExitoso by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set

    // Validaciones en tiempo real
    val formularioValido: Boolean
        get() = nombre.isNotEmpty() &&
                emailValid &&
                password.length >= 6 &&
                passwordsMatch &&
                aceptaTerminos

    val emailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val passwordsMatch: Boolean
        get() = password == confirmPassword && password.isNotEmpty()

    // Funciones para actualizar campos
    fun updateNombre(nuevoNombre: String) { nombre = nuevoNombre }
    fun updateEmail(nuevoEmail: String) { email = nuevoEmail }
    fun updatePassword(nuevaPassword: String) { password = nuevaPassword }
    fun updateConfirmPassword(confirm: String) { confirmPassword = confirm }
    fun updateTelefono(nuevoTelefono: String) { telefono = nuevoTelefono }
    fun updateAceptaTerminos(acepta: Boolean) { aceptaTerminos = acepta }

    // LÓGICA DE REGISTRO CON TU CLASE Usuarios
    fun registrarUsuario() {
        if (!formularioValido) {
            errorMessage = "Por favor completa todos los campos correctamente"
            return
        }

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                delay(1000) // Pequeña simulación de delay

                // LÓGICA REAL DE REGISTRO CON TU CLASE Usuarios
                // 1. Verificar si el usuario ya existe
                if (Usuarios.existeUsuario(email)) {
                    errorMessage = "Este email ya está registrado"
                    return@launch
                }

                // 2. Crear nuevo usuario
                val nuevoUsuario = User(
                    nombre = nombre,
                    email = email,
                    password = password, // En una app real, esto debería estar encriptado
                    telefono = telefono
                )

                // 3. Agregar a la lista de usuarios
                Usuarios.agregarUsuario(nuevoUsuario)

                // 4. Éxito
                registroExitoso = true
                println("Usuario registrado: ${nuevoUsuario.email}")
                println("Total usuarios: ${Usuarios.users.size}")

            } catch (e: Exception) {
                errorMessage = "Error al registrar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun resetFormulario() {
        nombre = ""
        email = ""
        password = ""
        confirmPassword = ""
        telefono = ""
        aceptaTerminos = false
        errorMessage = ""
        registroExitoso = false
    }

    fun clearError() {
        errorMessage = ""
    }
}
