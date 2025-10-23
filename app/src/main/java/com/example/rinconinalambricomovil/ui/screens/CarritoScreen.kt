package com.example.elrinconinalambrico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rinconinalambricomovil.model.CartItem
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoVM: CarritoViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (carritoVM.itemCount > 0) {
                        IconButton(onClick = { carritoVM.clear() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Vaciar carrito")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (carritoVM.itemCount > 0) {
                Surface(shadowElevation = 8.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total: ${carritoVM.formatCLP(carritoVM.total)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Button(onClick = { /* TODO: flujo de compra */ }) {
                            Text("Comprar")
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (carritoVM.itemCount == 0) {
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
            ) {
                Text("Tu carrito está vacío.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carritoVM.items) { item ->
                    CartRow(item, carritoVM)
                }
            }
        }
    }
}

@Composable
private fun CartRow(item: CartItem, carritoVM: CarritoViewModel) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
            Text(
                "Precio: ${carritoVM.formatCLP(item.producto.precio)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = { carritoVM.minusOne(item.producto) }) { Text("-") }
                Text("Cantidad: ${item.cantidad}")
                OutlinedButton(onClick = { carritoVM.add(item.producto) }) { Text("+") }
                Spacer(Modifier.weight(1f))
                Text(
                    "Subtotal: " + carritoVM.formatCLP(item.producto.precio * item.cantidad),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { carritoVM.remove(item.producto) }) { Text("Eliminar") }
        }
    }
}
