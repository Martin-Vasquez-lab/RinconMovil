package com.example.rinconinalambricomovil.ui.state

import com.example.rinconinalambricomovil.model.Categoria
import com.example.rinconinalambricomovil.model.Producto
import org.junit.Test
import org.junit.Assert.*

class CarritoViewModelTest {

    @Test
    fun productoTieneDatosCorrectos() {
        val producto = Producto(
            id = 1,
            nombre = "Producto Test",
            precio = 1000.0,
            descripcion = "Descripción test",
            categoria = Categoria.MANGA,
            imagenRes = 0
        )

        assertEquals(1, producto.id)
        assertEquals("Producto Test", producto.nombre)
        assertEquals(1000.0, producto.precio, 0.0)
        assertEquals(Categoria.MANGA, producto.categoria)
    }

    @Test
    fun precioMayorACero() {
        val producto = Producto(
            id = 1,
            nombre = "Test",
            precio = 1500.0,
            descripcion = "Test",
            categoria = Categoria.MANGA,
            imagenRes = 0
        )

        assertTrue(producto.precio > 0)
    }

    @Test
    fun categoriaEsValida() {
        val producto = Producto(
            id = 1,
            nombre = "Test",
            precio = 1000.0,
            descripcion = "Test",
            categoria = Categoria.AUDIFONOS,
            imagenRes = 0
        )

        assertTrue(producto.categoria in listOf(Categoria.MANGA, Categoria.AUDIFONOS, Categoria.FIGURAS, Categoria.CONSOLAS))
    }
}
