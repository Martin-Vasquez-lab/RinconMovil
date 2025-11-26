package com.example.rinconinalambricomovil.model

data class PedidoRequest(
    val usuarioId: Int,
    val items: List<PedidoItemRequest>,
    val formaPagoId: Int,
    val monto: Double
)

