package com.example.rinconinalambricomovil.data

import com.example.rinconinalambricomovil.model.User

object Usuarios {
    val users = mutableListOf(
        User("Loren","loren@gmail.com","1234", "123456789"),
        User("Admin", "admin@rincon.com", "admin123", "987654321")
    )

    fun validateUser(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }

    fun getUser(email: String): User? {
        return users.find { it.email == email }
    }

    fun existeUsuario(email: String): Boolean {
        return users.any { it.email == email }
    }

    fun agregarUsuario(usuario: User) {
        users.add(usuario)
    }

    fun getTotalUsuarios(): Int {
        return users.size
    }
}
