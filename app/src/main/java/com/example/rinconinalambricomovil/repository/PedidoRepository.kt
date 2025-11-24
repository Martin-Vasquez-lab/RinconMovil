package com.example.rinconinalambricomovil.repository

import com.example.rinconinalambricomovil.data.RetrofitClient
import com.example.rinconinalambricomovil.model.PedidoRequest
import com.example.rinconinalambricomovil.model.PedidoResponse
import retrofit2.Response

class PedidoRepository {

    suspend fun enviarPedido(pedido: PedidoRequest): Response<PedidoResponse> {
        return RetrofitClient.pedidoApi.crearPedido(pedido)
    }
}
