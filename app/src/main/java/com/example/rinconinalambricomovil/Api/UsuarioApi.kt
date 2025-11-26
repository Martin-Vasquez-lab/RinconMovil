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


    @POST("usuario/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>

    @POST("usuario")
    suspend fun registrarUsuario(@Body usuario: User): Response<User>


    @GET("usuario")
    suspend fun getUsuarios(): Response<List<User>>

}
