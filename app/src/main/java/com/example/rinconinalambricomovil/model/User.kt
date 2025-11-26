package com.example.rinconinalambricomovil.model

import com.google.gson.annotations.SerializedName


data class User(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("rut")
    val rut: String,

    @SerializedName("nombre")
    val nombre: String,


    @SerializedName("correo")
    val email: String,


    @SerializedName("contrasena")
    val password: String,

    @SerializedName("telefono")
    val telefono: String,

    @SerializedName("rol")
    val rol: Rol
)
