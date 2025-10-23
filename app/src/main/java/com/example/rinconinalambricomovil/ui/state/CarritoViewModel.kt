package com.example.rinconinalambricomovil.ui.state

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.rinconinalambricomovil.model.CartItem
import com.example.rinconinalambricomovil.model.Producto
import java.text.NumberFormat
import java.util.Locale

class CarritoViewModel : ViewModel() {
    private val _items = mutableStateListOf<CartItem>()
    val items: List<CartItem> get() = _items

    private val clp = NumberFormat.getCurrencyInstance(Locale("es","CL")).apply {
        maximumFractionDigits = 0
    }

    fun add(p: Producto) {
        val i = _items.indexOfFirst { it.producto.id == p.id }
        if (i >= 0) _items[i] = _items[i].copy(cantidad = _items[i].cantidad + 1)
        else _items.add(CartItem(p, 1))
    }
    fun minusOne(p: Producto) {
        val i = _items.indexOfFirst { it.producto.id == p.id }
        if (i >= 0) {
            val it = _items[i]
            if (it.cantidad > 1) _items[i] = it.copy(cantidad = it.cantidad - 1)
            else _items.removeAt(i)
        }
    }
    fun remove(p: Producto) { _items.removeAll { it.producto.id == p.id } }
    fun clear() = _items.clear()

    val itemCount get() = _items.sumOf { it.cantidad }
    val total get() = _items.sumOf { it.producto.precio * it.cantidad }
    fun formatCLP(v: Double) = clp.format(v)
}