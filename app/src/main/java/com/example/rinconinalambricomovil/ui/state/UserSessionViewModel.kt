package com.example.rinconinalambricomovil.ui.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rinconinalambricomovil.model.User

class UserSessionViewModel : ViewModel() {

    // Estado del usuario logeado
    private val _usuarioActual = mutableStateOf<User?>(null)
    val usuarioActual: State<User?> = _usuarioActual

    // 👉 ESTA es la función que estás llamando en LoginScreen
    fun iniciarSesion(user: User) {
        _usuarioActual.value = user
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}
