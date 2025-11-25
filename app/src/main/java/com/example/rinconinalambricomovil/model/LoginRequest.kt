package com.example.rinconinalambricomovil.model

import com.google.gson.annotations.SerializedName



data class LoginRequest(
    // En Kotlin lo llamamos 'email', pero se enviará como 'correo' en el JSON.
    @SerializedName("correo")
    val email: String,

    // En Kotlin lo llamamos 'password', pero se enviará como 'contrasena' en el JSON.
    @SerializedName("contrasena")
    val password: String
)
