package com.example.elrinconinalambrico.data

import com.example.rinconinalambricomovil.model.Producto
import com.example.rinconinalambricomovil.model.Categoria
object SampleData {
    val productos = listOf(
        // MANGA
        Producto(1, "One Piece Vol. 1", 8990.0, "Edición en español", Categoria.MANGA),
        Producto(2, "Jujutsu Kaisen Vol. 3", 8990.0, "Papel premium", Categoria.MANGA),
        // AUDÍFONOS
        Producto(3, "Audífonos BT Pro", 29990.0, "ANC + 30h batería", Categoria.AUDIFONOS),
        Producto(4, "Headset Gamer X", 49990.0, "7.1 virtual RGB", Categoria.AUDIFONOS),
        // FIGURAS
        Producto(5, "Figura Tanjiro 18cm", 24990.0, "PVC pintado a mano", Categoria.FIGURAS),
        Producto(6, "Figura Zelda 20cm", 29990.0, "Coleccionista", Categoria.FIGURAS),
        // CONSOLAS
        Producto(7, "Consola Pocket Retro", 39990.0, "5k juegos retro", Categoria.CONSOLAS),
        Producto(8, "Control Pro Inalámbrico", 19990.0, "BT 5.0 + vibración", Categoria.CONSOLAS)
    )

    fun porCategoria(cat: Categoria) = productos.filter { it.categoria == cat }
}