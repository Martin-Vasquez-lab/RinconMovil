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
        val idx = _items.indexOfFirst { it.producto.id == p.id }
        if (idx >= 0) _items[idx] = _items[idx].copy(cantidad = _items[idx].cantidad + 1)
        else _items.add(CartItem(p, 1))
    }
    fun minusOne(p: Producto) {
        val idx = _items.indexOfFirst { it.producto.id == p.id }
        if (idx >= 0) {
            val it = _items[idx]
            if (it.cantidad > 1) _items[idx] = it.copy(cantidad = it.cantidad - 1)
            else _items.removeAt(idx)
        }
    }
    fun remove(p: Producto) { _items.removeAll { it.producto.id == p.id } }
    fun clear() = _items.clear()

    val itemCount get() = _items.sumOf { it.cantidad }
    val total get() = _items.sumOf { it.producto.precio * it.cantidad }

    fun money(v: Double) = clp.format(v)
}