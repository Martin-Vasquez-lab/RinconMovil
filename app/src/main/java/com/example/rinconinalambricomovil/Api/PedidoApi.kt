package com.example.rinconinalambricomovil.Api

import com.example.rinconinalambricomovil.model.PedidoRequest
import com.example.rinconinalambricomovil.model.PedidoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PedidoApi {

   // @POST("/pedido/crear")
    //suspend fun crearPedido(
    //    @Body pedido: PedidoRequest
    //): Response<PedidoResponse>
   @POST("/fpago/crearPedido")
   suspend fun crearPedido(@Body pedido: PedidoRequest): Response<PedidoResponse>


}
