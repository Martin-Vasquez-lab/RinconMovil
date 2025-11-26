package com.example.rinconinalambricomovil.components.Login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.data.RetrofitClient // Importamos Retrofit
import com.example.rinconinalambricomovil.model.LoginRequest // Importamos el DTO de login
import com.example.rinconinalambricomovil.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // Estados posibles del login
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: User) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // --- FUNCIÓN DE LOGIN CORREGIDA PARA USAR LA API ---
    fun login(email: String, password: String) {
        // Validar que el email tenga un formato correcto antes de llamar a la API
        if (!isValidEmail(email)) {
            _loginState.value = LoginState.Error("El formato del email no es válido.")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                // 1. Crear el objeto que se enviará al backend
                val request = LoginRequest(email = email, password = password)

                // 2. Llamar a la API que configuramos en RetrofitClient
                val response = RetrofitClient.usuarioApi.login(request)

                // 3. Procesar la respuesta del servidor
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        // ¡ÉXITO! El login es correcto y tenemos los datos del usuario.
                        println("Usuario logeado vía API: ${user.email}")
                        _loginState.value = LoginState.Success(user)
                    } else {
                        // El servidor respondió OK (200) pero el cuerpo estaba vacío.
                        _loginState.value = LoginState.Error("Respuesta vacía del servidor.")
                    }
                } else {
                    // El servidor respondió con un error (ej. 401 No Autorizado si las credenciales son incorrectas)
                    _loginState.value = LoginState.Error("Email o contraseña incorrectos.")
                }

            } catch (e: Exception) {
                // Ocurrió un error de red (ej. no hay internet, el servidor está caído)
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
