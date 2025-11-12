package com.example.rinconinalambricomovil.data

import com.example.rinconinalambricomovil.model.User
object Usuarios {
    val users = listOf(
        User("loren@gmail.com","1234"),
        User("admin@rincon.com", "admin123")

    )



    //Validacion
    fun validateUser(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }

    fun getUser(email: String): User? {
        return users.find { it.email == email }
    }

}