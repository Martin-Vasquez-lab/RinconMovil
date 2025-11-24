package com.example.rinconinalambricomovil.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rinconinalambricomovil.model.PedidoRequest
import com.example.rinconinalambricomovil.model.PedidoResponse
import com.example.rinconinalambricomovil.repository.PedidoRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PedidoViewModel : ViewModel() {

    private val repository = PedidoRepository()

    var resultado: Response<PedidoResponse>? = null
        private set

    fun enviarPedido(pedido: PedidoRequest, onFinish: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.enviarPedido(pedido)
                resultado = response

                if (response.isSuccessful) {
                    val mensaje = response.body()?.mensaje ?: "Pedido realizado"
                    onFinish(true, mensaje)
                } else {
                    onFinish(false, "Error del servidor: ${response.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                onFinish(false, "Error de conexión: ${e.message}")
            }
        }
    }
}

