package com.example.rinconinalambricomovil.model

data class PedidoRequest(
    val usuarioId: Int,
    val carroId: Int,
    val formaPagoId: Int,
    val monto: Double
)

