package com.example.rinconinalambricomovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.rinconinalambricomovil.model.Categoria
import com.example.rinconinalambricomovil.components.CartIconWithMenu
import com.example.rinconinalambricomovil.ui.navigation.Routes
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import androidx.compose.material3.CenterAlignedTopAppBar
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    nav: NavController,
    carritoVM: CarritoViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("El Rincón Inalámbrico") },
                actions = {
                    CartIconWithMenu(
                        carritoVM = carritoVM,
                        onVerCarrito = { nav.navigate(Routes.CARRITO) }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Categorías", style = MaterialTheme.typography.titleMedium)
            // Botones de categorías
            CategoryButton("Manga") { nav.navigate("categoria/${Categoria.MANGA.name}") }
            CategoryButton("Audífonos") { nav.navigate("categoria/${Categoria.AUDIFONOS.name}") }
            CategoryButton("Figuras") { nav.navigate("categoria/${Categoria.FIGURAS.name}") }
            CategoryButton("Consolas") { nav.navigate("categoria/${Categoria.CONSOLAS.name}") }

            Button(
                onClick = { nav.navigate(Routes.LOGIN) },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Ir a Login")
            }
        }
    }
}

@Composable
private fun CategoryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(text)
    }
}

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Routes.LOGIN) }) {
            Text("Ir a Login")
        }
    }
}
