package com.example.rinconinalambricomovil.Api

import com.example.rinconinalambricomovil.model.Producto
import retrofit2.http.GET

interface  CatalogoApi {
    @GET("/api/productos")  // Cambiar de "/producto" a "/api/productos"
    suspend fun getProductos(): List<Producto>
}


