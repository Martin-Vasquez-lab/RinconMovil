package com.example.rinconinalambricomovil.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*

import com.example.rinconinalambricomovil.ui.state.CarritoViewModel

@Composable
fun CartIconWithMenu(
    carritoVM: CarritoViewModel,
    onVerCarrito: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    BadgedBox(
        badge = {
            if (carritoVM.itemCount > 0) Badge { Text("${carritoVM.itemCount}") }
        }
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
        }
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        if (carritoVM.itemCount == 0) {
            DropdownMenuItem(
                text = { Text("Carrito vacío") },
                onClick = { expanded = false }
            )
        } else {
            // muestra hasta 4 items para no alargar el menú
            carritoVM.items.take(4).forEach { item ->
                DropdownMenuItem(
                    text = { Text("${item.cantidad}× ${item.producto.nombre}") },
                    onClick = { /* no-op */ }
                )
            }
            DropdownMenuItem(
                text = { Text("Total: ${carritoVM.money(carritoVM.total)}") },
                onClick = { /* no-op */ }
            )
            DropdownMenuItem(
                text = { Text("Ver carrito") },
                onClick = {
                    expanded = false
                    onVerCarrito()
                }
            )
        }
    }
}