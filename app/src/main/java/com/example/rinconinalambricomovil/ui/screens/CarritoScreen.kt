package com.example.rinconinalambricomovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rinconinalambricomovil.model.CartItem
import com.example.rinconinalambricomovil.ui.state.CarritoViewModel
import kotlinx.coroutines.launch


private enum class PaymentMethod {
    CARD, PAYPAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoVM: CarritoViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado del formulario de pago
    var paymentMethod by remember { mutableStateOf(PaymentMethod.CARD) }
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }
    var paypalEmail by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (carritoVM.itemCount > 0) {
                        IconButton(onClick = { carritoVM.clear() }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Vaciar carrito"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            if (carritoVM.itemCount > 0) {
                Surface(shadowElevation = 8.dp) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total: ${carritoVM.money(carritoVM.total)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Button(
                            onClick = {
                                val cardValid = when (paymentMethod) {
                                    PaymentMethod.CARD -> {
                                        cardName.isNotBlank() &&
                                                cardNumber.length == 16 &&
                                                cardExpiry.matches(Regex("\\d{2}/\\d{2}")) && // MM/AA
                                                cardCvv.length == 3
                                    }
                                    PaymentMethod.PAYPAL -> false
                                }

                                val paypalValid = when (paymentMethod) {
                                    PaymentMethod.PAYPAL -> {
                                        paypalEmail.isNotBlank() &&
                                                "@" in paypalEmail && "." in paypalEmail.substringAfter("@")
                                    }
                                    PaymentMethod.CARD -> false
                                }

                                val isValid = when (paymentMethod) {
                                    PaymentMethod.CARD -> cardValid
                                    PaymentMethod.PAYPAL -> paypalValid
                                }

                                scope.launch {
                                    if (!isValid) {
                                        snackBarHostState.showSnackbar(
                                            "Revisa los datos de pago antes de continuar"
                                        )
                                    } else {
                                        snackBarHostState.showSnackbar("Compra realizada con éxito")
                                        carritoVM.clear()
                                    }
                                }
                            }
                        ) {
                            Text("Comprar")
                        }

                    }
                }
            }
        }
    ) { padding ->
        if (carritoVM.itemCount == 0) {
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text("Tu carrito está vacío.")
            }
        } else {
            LazyColumn(
                Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Lista de productos en el carrito
                items(items = carritoVM.items, key = { it.producto.id }) { item ->
                    CartRow(item, carritoVM)
                }

                // Sección de pago al final de la lista
                item {
                    Spacer(Modifier.height(16.dp))
                    PaymentSection(
                        paymentMethod = paymentMethod,
                        onPaymentMethodChange = { paymentMethod = it },
                        cardName = cardName,
                        onCardNameChange = { cardName = it },
                        cardNumber = cardNumber,
                        onCardNumberChange = { cardNumber = it },
                        cardExpiry = cardExpiry,
                        onCardExpiryChange = { cardExpiry = it },
                        cardCvv = cardCvv,
                        onCardCvvChange = { cardCvv = it },
                        paypalEmail = paypalEmail,
                        onPaypalEmailChange = { paypalEmail = it }
                    )
                    Spacer(Modifier.height(72.dp)) // espacio para que no tape el bottomBar
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
            Text("Precio: ${carritoVM.money(item.producto.precio)}")
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
                    "Subtotal: ${
                        carritoVM.money(
                            item.producto.precio * item.cantidad
                        )
                    }"
                )
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { carritoVM.remove(item.producto) }) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
private fun PaymentSection(
    paymentMethod: PaymentMethod,
    onPaymentMethodChange: (PaymentMethod) -> Unit,
    cardName: String,
    onCardNameChange: (String) -> Unit,
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    cardExpiry: String,
    onCardExpiryChange: (String) -> Unit,
    cardCvv: String,
    onCardCvvChange: (String) -> Unit,
    paypalEmail: String,
    onPaypalEmailChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Método de pago", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                RadioButton(
                    selected = paymentMethod == PaymentMethod.CARD,
                    onClick = { onPaymentMethodChange(PaymentMethod.CARD) }
                )
                Spacer(Modifier.width(4.dp))
                Text("Débito / Crédito")
            }
            Row {
                RadioButton(
                    selected = paymentMethod == PaymentMethod.PAYPAL,
                    onClick = { onPaymentMethodChange(PaymentMethod.PAYPAL) }
                )
                Spacer(Modifier.width(4.dp))
                Text("PayPal")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (paymentMethod == PaymentMethod.CARD) {
            OutlinedTextField(
                value = cardName,
                onValueChange = { input ->
                    
                    val filtered = input
                        .filter { it.isLetter() || it.isWhitespace() }
                        .take(40)

                    onCardNameChange(filtered)
                },
                label = { Text("Nombre en la tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { input ->
                    val digits = input.filter { it.isDigit() }.take(16)
                    onCardNumberChange(digits)
                },
                label = { Text("Número de tarjeta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = cardExpiry,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }.take(4)
                        val formatted = when {
                            digits.length <= 2 -> digits
                            else -> digits.substring(0, 2) + "/" + digits.substring(2)
                        }
                        onCardExpiryChange(formatted)
                    },
                    label = { Text("MM/AA") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = cardCvv,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }.take(3)
                        onCardCvvChange(digits)
                    },
                    label = { Text("CVV") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

            }
        } else {
            OutlinedTextField(
                value = paypalEmail,
                onValueChange = onPaypalEmailChange,
                label = { Text("Correo de PayPal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}
