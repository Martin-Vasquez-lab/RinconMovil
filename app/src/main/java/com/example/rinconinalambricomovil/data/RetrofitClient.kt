package com.example.rinconinalambricomovil.data


import com.example.rinconinalambricomovil.api.PedidoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.rinconinalambricomovil.Api.CatalogoApi



object RetrofitClient {

    // 👉 Usa la IP de tu PC (para celular real)
    private const val BASE_URL = "http://192.168.1.5:8083/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pedidoApi: PedidoApi by lazy {
        retrofit.create(PedidoApi::class.java)
    }
    val catalogoApi: CatalogoApi by lazy {
        retrofit.create(CatalogoApi::class.java)
    }

}


