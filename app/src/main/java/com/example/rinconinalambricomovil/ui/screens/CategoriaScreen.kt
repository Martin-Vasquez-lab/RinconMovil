package com.example.rinconinalambricomovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elrinconinalambrico.data.SampleData
import com.example.rinconinalambricomovil.components.CartIconWithMenu
import com.example.rinconinalambricomovil.model.Categoria
import com.example.rinconinalambricomovil.model.Producto
import com.example.rinconinalambricomovil.ui.navigation.Routes
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CategoriaScreen(
    nav: NavController,
    carritoVM: CarritoViewModel,
    categoriaParam: String
) {
    val categoria = remember(categoriaParam) {
        runCatching { Categoria.valueOf(categoriaParam) }.getOrElse { Categoria.MANGA }
    }
    val productos = remember(categoria) { SampleData.porCategoria(categoria) }
    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoria.name.lowercase().replaceFirstChar { it.uppercase() },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    CartIconWithMenu(
                        carritoVM = carritoVM,
                        onVerCarrito = { nav.navigate(Routes.CARRITO) }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = productos, key = { it.id }) { p ->
                ProductCard(
                    producto = p,
                    onAddToCart = { producto ->
                        carritoVM.add(producto)
                        // Launch the snackbar from a coroutine scope
                        scope.launch {
                            snack.showSnackbar("${producto.nombre} agregado al carrito")
                        }
                    }
                )
            }
            item { Spacer(Modifier.height(72.dp)) }
        }
    }
}

@Composable
private fun ProductCard(
    producto: Producto,
    onAddToCart: (Producto) -> Unit // Modified parameter
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(2.dp))
            // Assuming you add this helper function to CarritoViewModel
            Text(producto.precio.toString(), color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(6.dp))
            Text(producto.descripcion, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    onAddToCart(producto) // Call the lambda with the product
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Agregar al carrito") }
        }
    }
}
