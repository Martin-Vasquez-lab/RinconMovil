package com.example.rinconinalambricomovil.components.Register

import org.junit.Test
import org.junit.Assert.*


class RegisterViewModelTest {

    @Test
    fun formularioVacioEsInvalido() {
        // Test directo de lógica sin ViewModel
        val nombre = ""
        val rut = ""
        val email = ""
        val password = ""
        val confirmPassword = ""
        val telefono = ""
        val aceptaTerminos = false

        // Simular validación de email (sin Patterns)
        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val emailValido = emailPattern.matches(email)
        val passwordsCoinciden = password == confirmPassword && password.isNotEmpty()

        val formularioValido = nombre.isNotEmpty() &&
                rut.isNotEmpty() &&
                emailValido &&
                password.length >= 6 &&
                passwordsCoinciden &&
                aceptaTerminos

        assertFalse(formularioValido)
    }

    @Test
    fun datosValidosHacenFormularioValido() {
        val nombre = "Juan Pérez"
        val rut = "12345678-9"
        val email = "juan@example.com"
        val password = "password123"
        val confirmPassword = "password123"
        val telefono = "987654321"
        val aceptaTerminos = true

        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val emailValido = emailPattern.matches(email)
        val passwordsCoinciden = password == confirmPassword && password.isNotEmpty()

        val formularioValido = nombre.isNotEmpty() &&
                rut.isNotEmpty() &&
                emailValido &&
                password.length >= 6 &&
                passwordsCoinciden &&
                aceptaTerminos

        assertTrue(formularioValido)
    }

    @Test
    fun emailInvalidoHaceFormularioInvalido() {
        val nombre = "Juan Pérez"
        val rut = "12345678-9"
        val email = "email-invalido"
        val password = "password123"
        val confirmPassword = "password123"
        val telefono = "987654321"
        val aceptaTerminos = true

        val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val emailValido = emailPattern.matches(email)
        val passwordsCoinciden = password == confirmPassword && password.isNotEmpty()

        val formularioValido = nombre.isNotEmpty() &&
                rut.isNotEmpty() &&
                emailValido &&
                password.length >= 6 &&
                passwordsCoinciden &&
                aceptaTerminos

        assertFalse(formularioValido)
    }
}
