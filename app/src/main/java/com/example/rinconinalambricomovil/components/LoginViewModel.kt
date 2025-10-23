package com.example.rinconinalambricomovil.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            // Simular validación (aquí iría tu API real)
            delay(1000)

            if (email.isNotEmpty() && password.length >= 6) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Email o contraseña inválidos")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}