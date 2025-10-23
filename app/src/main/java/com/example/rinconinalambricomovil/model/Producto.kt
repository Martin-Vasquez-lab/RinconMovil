package com.example.rinconinalambricomovil.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val categoria: Categoria
)

enum class Categoria { MANGA, AUDIFONOS, FIGURAS, CONSOLAS }

