package com.example.elrinconinalambrico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rinconinalambricomovil.data.CarritoManager
import com.example.rinconinalambricomovil.model.Producto

@OptIn(ExperimentalMaterial3Api::class)  // ← AGREGA ESTA LÍNEA
@Composable
fun MenuScreen() {
    val productos = listOf(
        Producto(1, "Audífonos Bluetooth", 29990.0, "Alta calidad de sonido"),
        Producto(2, "Mouse inalámbrico", 15990.0, "Sensor óptico de precisión"),
        Producto(3, "Teclado mecánico RGB", 49990.0, "Switches rojos silenciosos"),
        Producto(4, "Cargador rápido", 12990.0, "Carga 3 veces más veloz")
    )

    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("El Rincón Inalámbrico") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (mensaje.isNotEmpty()) {
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                LazyColumn {
                    items(productos) { producto ->  // ← Cambié esto por "items(productos)"
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("$${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                                Text(producto.descripcion, style = MaterialTheme.typography.bodySmall)

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        CarritoManager.agregarProducto(producto)
                                        mensaje = "${producto.nombre} agregado al carrito 🛒"
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Agregar al carrito")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}