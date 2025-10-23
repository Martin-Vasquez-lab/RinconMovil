package com.example.rinconinalambricomovil.data

import com.example.rinconinalambricomovil.model.Producto

object CarritoManager {
    private val carrito = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        carrito.add(producto)
    }

    fun obtenerCarrito(): List<Producto> = carrito

    fun vaciar() { carrito.clear() }
}

