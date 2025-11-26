package com.example.rinconinalambricomovil.components.Register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.data.RetrofitClient
import com.example.rinconinalambricomovil.model.Rol
import com.example.rinconinalambricomovil.model.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // Estado del formulario
    var nombre by mutableStateOf("")
        private set
    var rut by mutableStateOf("") // <-- AÑADIDO
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
                rut.isNotEmpty() &&
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
    fun updateRut(nuevoRut: String) { rut = nuevoRut } // <-- AÑADIDO
    fun updateEmail(nuevoEmail: String) { email = nuevoEmail }
    fun updatePassword(nuevaPassword: String) { password = nuevaPassword }
    fun updateConfirmPassword(confirm: String) { confirmPassword = confirm }
    fun updateTelefono(nuevoTelefono: String) { telefono = nuevoTelefono }
    fun updateAceptaTerminos(acepta: Boolean) { aceptaTerminos = acepta }

    // --- LÓGICA DE REGISTRO CONECTADA AL BACKEND ---
    fun registrarUsuario() {
        if (!formularioValido) {
            errorMessage = "Por favor completa todos los campos correctamente"
            return
        }

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val nuevoUsuario = User(
                    rut = rut,
                    nombre = nombre,
                    email = email,
                    password = password, // En una app real, esto debería estar encriptado ANTES de enviarse
                    telefono = telefono, // Convertir a Int
                    rol = Rol(id = 2, nombre = "CLIENTE") // Asignar un rol por defecto (ej. ID 2 para Cliente)
                )

                //  Llamar a la API de Retrofit para registrar el usuario
                val response = RetrofitClient.usuarioApi.registrarUsuario(nuevoUsuario)

                // Procesar la respuesta
                if (response.isSuccessful) {
                    registroExitoso = true
                    println("Usuario registrado a través de la API: ${response.body()?.email}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorMessage = "Error en el registro: $errorBody"
                    println("Error del servidor: $errorBody")
                }

            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                println("Error de conexión: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun resetFormulario() {
        nombre = ""
        rut = ""
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
