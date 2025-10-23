package com.example.rinconinalambricomovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import com.example.rinconinalambricomovil.ui.screens.MenuScreen
import com.example.rinconinalambricomovil.ui.theme.RinconInalambricoMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RinconInalambricoMovilTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val carritoVM: CarritoViewModel = viewModel()
                    MenuScreen(carritoVM = carritoVM)
                }
            }
        }
    }
}