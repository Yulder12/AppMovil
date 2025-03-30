package com.example.appmovil.data

data class SoftwareProduct(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val developer: String,
    val rating: Float,
    val downloads: Int,
    val imageUrl: String = "https://via.placeholder.com/150"
)