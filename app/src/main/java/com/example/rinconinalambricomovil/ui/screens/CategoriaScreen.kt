package com.example.rinconinalambricomovil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.rinconinalambricomovil.data.SampleData
import com.example.rinconinalambricomovil.components.CartIconWithMenu
import com.example.rinconinalambricomovil.model.Categoria
import com.example.rinconinalambricomovil.model.Producto
import com.example.rinconinalambricomovil.ui.navigation.Routes
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaScreen(
    nav: NavController,
    carritoVM: CarritoViewModel,
    categoriaParam: String
) {
    val categoria = remember(categoriaParam) {
        runCatching { Categoria.valueOf(categoriaParam) }.getOrElse { Categoria.MANGA }
    }

    val productos = remember(categoria) {
        SampleData.porCategoria(categoria)
    }

    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
            items(
                items = productos,
                key = { it.id }
            ) { p ->
                ProductCard(
                    producto = p,
                    onAddToCart = { producto ->
                        carritoVM.add(producto)
                        scope.launch {
                            snack.showSnackbar("${producto.nombre} agregado al carrito")
                        }
                    }
                )
            }

            item {
                Spacer(Modifier.height(72.dp))
            }
        }
    }
}

@Composable
private fun ProductCard(
    producto: Producto,
    onAddToCart: (Producto) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // 🖼 Imagen del producto
            Image(
                painter = painterResource(id = producto.imagenRes),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Column(Modifier.padding(16.dp)) {

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "$${producto.precio.toInt()}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { onAddToCart(producto) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}
