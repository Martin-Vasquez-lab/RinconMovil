package com.example.rinconinalambricomovil.ui.state

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.rinconinalambricomovil.data.SampleData
import com.example.rinconinalambricomovil.model.CartItem
import com.example.rinconinalambricomovil.model.Producto
import org.json.JSONArray
import org.json.JSONObject
import com.example.rinconinalambricomovil.model.PedidoItemRequest
import com.example.rinconinalambricomovil.model.PedidoRequest



class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    // Lista observable para Compose
    var items by mutableStateOf<List<CartItem>>(emptyList())
        private set

    // Cantidad total de ítems
    val itemCount: Int
        get() = items.sumOf { it.cantidad }

    // Total en plata
    val total: Double
        get() = items.sumOf { it.producto.precio * it.cantidad }


    private val prefs =
        application.getSharedPreferences("carrito_prefs", Context.MODE_PRIVATE)

    init {

        cargarDesdePrefs()
    }

    // Formato para mostrar lucas
    fun money(value: Double): String =
        "$" + String.format("%,.0f", value)

    // ------------ Operaciones del carrito ------------

    fun add(producto: Producto) {
        val lista = items.toMutableList()
        val index = lista.indexOfFirst { it.producto.id == producto.id }
        if (index >= 0) {
            val actual = lista[index]
            lista[index] = actual.copy(cantidad = actual.cantidad + 1)
        } else {
            lista.add(CartItem(producto, 1))
        }
        items = lista
        guardarEnPrefs()
    }

    fun minusOne(producto: Producto) {
        val lista = items.toMutableList()
        val index = lista.indexOfFirst { it.producto.id == producto.id }
        if (index >= 0) {
            val actual = lista[index]
            if (actual.cantidad > 1) {
                lista[index] = actual.copy(cantidad = actual.cantidad - 1)
            } else {
                lista.removeAt(index)
            }
            items = lista
            guardarEnPrefs()
        }
    }

    fun crearPedidoRequest(
        nombre: String,
        direccion: String,
        telefono: String,
        metodoPago: String
    ): PedidoRequest {

        val itemsPedido = items.map { cartItem ->
            PedidoItemRequest(
                productoId = cartItem.producto.id,
                cantidad = cartItem.cantidad,
                precioUnitario = cartItem.producto.precio
            )
        }

        return PedidoRequest(
            usuarioId = 1,  // temporal
            carroId = 1,    // temporal
            formaPagoId = if (metodoPago == "TARJETA") 1 else 2,
            monto = total
        )

    }


    fun remove(producto: Producto) {
        items = items.filterNot { it.producto.id == producto.id }
        guardarEnPrefs()
    }

    fun clear() {
        items = emptyList()
        guardarEnPrefs()
    }

    // ------------ Persistencia en SharedPreferences ------------

    private fun guardarEnPrefs() {
        val jsonArray = JSONArray()
        for (item in items) {
            val obj = JSONObject()
            obj.put("id", item.producto.id)
            obj.put("cantidad", item.cantidad)
            jsonArray.put(obj)
        }
        prefs.edit()
            .putString("carrito_items", jsonArray.toString())
            .apply()
    }

    private fun cargarDesdePrefs() {
        val jsonStr = prefs.getString("carrito_items", null) ?: return
        try {
            val array = JSONArray(jsonStr)
            val lista = mutableListOf<CartItem>()
            val productosPorId = SampleData.productos.associateBy { it.id }

            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.getInt("id")
                val cantidad = obj.getInt("cantidad")
                val producto = productosPorId[id]
                if (producto != null) {
                    lista.add(CartItem(producto, cantidad))
                }
            }
            items = lista
        } catch (_: Exception) {
            // Si algo sale mal leyendo el JSON, simplemente no cargamos nada
        }
    }
}
