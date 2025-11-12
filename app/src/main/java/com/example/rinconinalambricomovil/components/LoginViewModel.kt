package com.example.rinconinalambricomovil.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.rinconinalambricomovil.data.Usuarios


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

            // Simular validación SIN API - solo validaciones básicas
            delay(2)

            // Validaciones locales sin API
            when {
                Usuarios.validateUser(email,password) ->{
                    val user = Usuarios.getUser(email)
                    println("Usuario logeado ${user?.email}")

                    _loginState.value= LoginState.Success
                }
                else -> {
                    // ✅ Si pasa todas las validaciones, login exitoso
                    _loginState.value = LoginState.Error("Gmail o contraseña no valida")
                }
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}