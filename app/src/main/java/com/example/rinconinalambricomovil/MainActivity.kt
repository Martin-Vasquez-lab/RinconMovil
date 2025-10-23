package com.example.rinconinalambricomovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rinconinalambricomovil.ui.navigation.Routes
import com.example.rinconinalambricomovil.ui.screens.CarritoScreen
import com.example.rinconinalambricomovil.ui.screens.CategoriaScreen
import com.example.rinconinalambricomovil.ui.screens.HomeScreen
import com.example.rinconinalambricomovil.ui.screens.LoginScreen
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import com.example.rinconinalambricomovil.ui.theme.RinconInalambricoMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RinconInalambricoMovilTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val nav = rememberNavController()
                    val carritoVM: CarritoViewModel = viewModel()

                    NavHost(navController = nav, startDestination = Routes.HOME) {
                        composable(Routes.HOME) {
                            HomeScreen(nav = nav, carritoVM = carritoVM)
                        }
                        composable(Routes.CATEGORIA) { backStack ->
                            val cat = backStack.arguments?.getString("cat") ?: "MANGA"
                            CategoriaScreen(nav = nav, carritoVM = carritoVM, categoriaParam = cat)
                        }
                        composable(Routes.CARRITO) {
                            CarritoScreen(navController = nav, carritoVM = carritoVM)
                        }
                        composable(Routes.LOGIN) {
                            LoginScreen(navController = nav)
                        }
                    }
                }
            }
        }
    }
}