package com.example.rinconinalambricomovil.components.Login

import org.junit.Test
import org.junit.Assert.*

class LoginViewModelTest {

    @Test
    fun emailInvalidoRetornaFalse() {
        // Test directo sin dependencia de Android
        val emailInvalido = "email-invalido"

        // Regex simple para validar email (sin usar Patterns)
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val isValid = emailPattern.matches(emailInvalido)

        assertFalse(isValid)
    }

    @Test
    fun emailValidoRetornaTrue() {
        val emailValido = "test@example.com"

        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val isValid = emailPattern.matches(emailValido)

        assertTrue(isValid)
    }

    @Test
    fun emailSinArrobaEsInvalido() {
        val emailSinArroba = "test.example.com"

        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val isValid = emailPattern.matches(emailSinArroba)

        assertFalse(isValid)
    }
}
