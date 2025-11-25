package com.example.rinconinalambricomovil.Api

import com.example.rinconinalambricomovil.model.LoginRequest
import com.example.rinconinalambricomovil.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interfaz que define la comunicación con el microservicio de Usuario.
 */
interface UsuarioApi {

    /**
     * Autentica a un usuario y devuelve sus datos si las credenciales son correctas.
     * Corresponde al endpoint de Spring Boot: @PostMapping("/usuario/login")
     * @param loginRequest El objeto [LoginRequest] con el correo y la contraseña.
     * @return Una respuesta con los datos del usuario autenticado.
     */
    @POST("usuario/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>

    /**
     * Registra un nuevo usuario en el sistema.
     * Corresponde al endpoint de Spring Boot: @PostMapping("/usuario")
     * @param usuario El objeto [User] con los datos para el registro.
     * @return Una respuesta con el usuario creado (incluyendo el ID que le asigna la base de datos).
     */
    @POST("usuario")
    suspend fun registrarUsuario(@Body usuario: User): Response<User>

    /**
     * Obtiene la lista COMPLETA de todos los usuarios registrados.
     * Corresponde al endpoint de Spring Boot: @GetMapping("/usuario")
     *
     * ADVERTENCIA: No usar para login. Es inseguro y poco eficiente.
     *
     * @return Una respuesta con la lista de todos los usuarios en la base de datos.
     */
    @GET("usuario")
    suspend fun getUsuarios(): Response<List<User>>

}
