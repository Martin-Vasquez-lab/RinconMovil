package com.example.rinconinalambricomovil.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.elrinconinalambrico.data.SampleData
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(carritoVM: CarritoViewModel) {
    val productos = remember { SampleData.productos }
    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("El Rincón Inalámbrico") }) },
        snackbarHost = { SnackbarHost(hostState = snack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = productos, key = { it.id }) { prod ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(prod.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(carritoVM.formatCLP(prod.precio))
                        Text(prod.descripcion, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                carritoVM.add(prod)
                                scope.launch {
                                    snack.showSnackbar("${prod.nombre} agregado al carrito")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Agregar al carrito") }
                    }
                }
            }
        }
    }
}