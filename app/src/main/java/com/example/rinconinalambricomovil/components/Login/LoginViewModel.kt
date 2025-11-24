package com.example.rinconinalambricomovil.components.Login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.data.Usuarios
import com.example.rinconinalambricomovil.model.User
import kotlinx.coroutines.delay
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

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            // Simular validación SIN API - solo validaciones básicas
            delay(2000)

            // Validaciones locales sin API
            if (Usuarios.validateUser(email, password)) {

                val user = Usuarios.getUser(email)

                if (user != null) {
                    println("Usuario logeado correctamente: ${user.email}")
                    _loginState.value = LoginState.Success(user)
                } else {
                    _loginState.value = LoginState.Error("No se pudo cargar el usuario.")
                }

            } else {
                _loginState.value = LoginState.Error("Email o contraseña no válidos")
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
