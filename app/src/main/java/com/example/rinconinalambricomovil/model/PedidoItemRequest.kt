package com.example.rinconinalambricomovil.model



data class PedidoItemRequest(
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: Double
)
