package com.example.rinconinalambricomovil.model

import com.google.gson.annotations.SerializedName


data class Rol(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String
)
