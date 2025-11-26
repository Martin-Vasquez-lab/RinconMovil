package com.example.rinconinalambricomovil.Api

import com.example.rinconinalambricomovil.model.CartItem
import retrofit2.Response
import retrofit2.http.*

interface CarritoApi {
    @GET("carro/usuario/{usuarioId}")
    suspend fun getCarritoUsuario(@Path("usuarioId") usuarioId: Int): Response<List<CartItem>>  // Cambiar List<Carro> por List<CartItem>

    @POST("carro/agregar")
    suspend fun agregarProducto(@Body request: AgregarProductoRequest): Response<CartItem>  // Cambiar Carro por CartItem

    @DELETE("carro/producto/{usuarioId}/{productoId}")
    suspend fun eliminarProducto(@Path("usuarioId") usuarioId: Int, @Path("productoId") productoId: Int): Response<Void>

    @DELETE("carro/vaciar/{usuarioId}")
    suspend fun vaciarCarrito(@Path("usuarioId") usuarioId: Int): Response<Void>
}

// DTO para agregar producto
data class AgregarProductoRequest(
    val usuarioId: Int,
    val productoId: Int,
    val cantidad: Int
)
