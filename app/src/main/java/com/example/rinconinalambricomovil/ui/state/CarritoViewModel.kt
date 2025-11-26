package com.example.rinconinalambricomovil.ui.state

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.Api.AgregarProductoRequest
import com.example.rinconinalambricomovil.data.RetrofitClient
import com.example.rinconinalambricomovil.data.SampleData
import com.example.rinconinalambricomovil.model.CartItem
import com.example.rinconinalambricomovil.model.PedidoItemRequest
import com.example.rinconinalambricomovil.model.PedidoRequest
import com.example.rinconinalambricomovil.model.Producto
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    // Lista observable para Compose
    var items by mutableStateOf<List<CartItem>>(emptyList())
        private set

    // Estado de sincronización
    var isLoading by mutableStateOf(false)
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

    // Cargar carrito desde backend
    fun cargarCarritoDesdeBackend(usuarioId: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.carritoApi.getCarritoUsuario(usuarioId)
                if (response.isSuccessful) {
                    val cartItems = response.body() ?: emptyList()
                    items = cartItems
                    guardarEnPrefs()
                }
            } catch (e: Exception) {
                // Si falla, usar datos locales
                cargarDesdePrefs()
            } finally {
                isLoading = false
            }
        }
    }

    // ------------ Operaciones del carrito ------------

    fun add(producto: Producto, usuarioId: Int? = null) {
        // Primero actualizar localmente para UI inmediata
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

        // Sincronizar con backend si hay usuario
        usuarioId?.let { syncAgregarProducto(it, producto.id, 1) }
    }

    fun minusOne(producto: Producto, usuarioId: Int? = null) {
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

            // Sincronizar con backend
            usuarioId?.let { syncEliminarProducto(it, producto.id) }
        }
    }

    fun remove(producto: Producto, usuarioId: Int? = null) {
        items = items.filterNot { it.producto.id == producto.id }
        guardarEnPrefs()
        usuarioId?.let { syncEliminarProducto(it, producto.id) }
    }

    fun clear() {
        items = emptyList()
        guardarEnPrefs()
    }

    fun syncVaciarCarrito(usuarioId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.carritoApi.vaciarCarrito(usuarioId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // ------------ FUNCIONES DE SINCRONIZACIÓN ------------

    private fun syncAgregarProducto(usuarioId: Int, productoId: Int, cantidad: Int) {
        viewModelScope.launch {
            try {
                val request = AgregarProductoRequest(usuarioId, productoId, cantidad)
                RetrofitClient.carritoApi.agregarProducto(request)
            } catch (e: Exception) {
                // Manejar error de sincronización
            }
        }
    }

    private fun syncEliminarProducto(usuarioId: Int, productoId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.carritoApi.eliminarProducto(usuarioId, productoId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // ------------ CREAR PEDIDO REQUEST ------------

    fun crearPedidoRequest(
        usuarioId: Int,
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
            usuarioId = usuarioId,
            items = itemsPedido,
            formaPagoId = if (metodoPago == "TARJETA") 1 else 2,
            monto = total)
    }

    // ------------ PERSISTENCIA LOCAL ------------

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
