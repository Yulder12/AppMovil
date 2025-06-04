package com.example.appmovil.data

data class Software(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val linkContacto: String = "",
    val autor: String = "",
    val version: String = ""
)

data class UserProfile(
    val uid: String = "",
    val nombre: String = "",
    val bio: String = "",
    val email: String = "",
    val fotoUrl: String = ""
)

