package com.example.rinconinalambricomovil.Api

import com.example.rinconinalambricomovil.model.Producto
import retrofit2.http.GET

interface CatalogoApi {
    @GET("/producto")
    suspend fun getProductos(): List<Producto>
}
