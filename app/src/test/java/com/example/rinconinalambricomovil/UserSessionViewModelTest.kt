package com.example.rinconinalambricomovil.ui.state

import com.example.rinconinalambricomovil.model.Rol
import com.example.rinconinalambricomovil.model.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class UserSessionViewModelTest {

    private lateinit var sessionViewModel: UserSessionViewModel

    @Before
    fun setup() {
        sessionViewModel = UserSessionViewModel()
    }

    @Test
    fun iniciarSesionActualizaUsuarioActual() {
        val mockUser = User(
            id = 1,
            rut = "12345678-9",
            nombre = "Juan Pérez",
            email = "juan@example.com",
            password = "hashed_password",
            telefono = "987654321",
            rol = Rol(id = 2, nombre = "CLIENTE")
        )

        sessionViewModel.iniciarSesion(mockUser)

        assertEquals(mockUser, sessionViewModel.usuarioActual.value)
    }

    @Test
    fun cerrarSesionHaceUsuarioActualNull() {
        val mockUser = User(
            id = 1,
            rut = "12345678-9",
            nombre = "Juan Pérez",
            email = "juan@example.com",
            password = "hashed_password",
            telefono = "987654321",
            rol = Rol(id = 2, nombre = "CLIENTE")
        )

        sessionViewModel.iniciarSesion(mockUser)
        sessionViewModel.cerrarSesion()

        assertNull(sessionViewModel.usuarioActual.value)
    }

    @Test
    fun usuarioActualEsNullInicialmente() {
        assertNull(sessionViewModel.usuarioActual.value)
    }
}
