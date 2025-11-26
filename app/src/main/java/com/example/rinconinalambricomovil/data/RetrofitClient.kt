package com.example.rinconinalambricomovil.data

import com.example.rinconinalambricomovil.Api.CatalogoApi
import com.example.rinconinalambricomovil.Api.PedidoApi
import com.example.rinconinalambricomovil.Api.UsuarioApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.rinconinalambricomovil.Api.CarritoApi

object RetrofitClient {


    // - "10.0.2.2":      Para el EMULADOR de Android Studio.
    // - "192.168.X.X":   Para un TELÉFONO FÍSICO
    private const val IP_ADDRESS = "10.0.2.2" // Cambiado para funcionar con el emulador

    // NOTA: URL base para el microservicio de 'pago'.

    private const val PAGO_BASE_URL = "http://$IP_ADDRESS:8083/"

    // NOTA: URL base para el microservicio de 'catalogo'.
    private const val CATALOGO_BASE_URL = "http://$IP_ADDRESS:8080/"

    // NOTA: URL base para el microservicio de 'usuario'.
    private const val USUARIO_BASE_URL = "http://$IP_ADDRESS:8081/"

    // NOTA: Esta es una función genérica para crear instancias de Retrofit.
    // Se reutiliza para no repetir código. Recibe la URL base del microservicio
    // al que queremos conectarnos.
    private fun getRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte el JSON de la respuesta a objetos de Kotlin
            .build()
    }

    // NOTA: Creación del servicio para la API de Pedidos.
    val pedidoApi: PedidoApi by lazy {
        getRetrofitInstance(PAGO_BASE_URL).create(PedidoApi::class.java)
    }

    // NOTA: Creación del servicio para la API de Catálogo.
    val catalogoApi: CatalogoApi by lazy {
        getRetrofitInstance(CATALOGO_BASE_URL).create(CatalogoApi::class.java)
    }

    // NOTA: Creación del servicio para la API de Usuario. (AÑADIDO)
    val usuarioApi: UsuarioApi by lazy {
        getRetrofitInstance(USUARIO_BASE_URL).create(UsuarioApi::class.java)
    }
    val carritoApi: CarritoApi by lazy {
        getRetrofitInstance(CATALOGO_BASE_URL).create(CarritoApi::class.java)
    }
}
