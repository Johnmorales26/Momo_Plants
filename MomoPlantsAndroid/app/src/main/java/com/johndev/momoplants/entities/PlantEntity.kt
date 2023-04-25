package com.johndev.momoplants.entities

data class PlantEntity(
    val id: Int,
    val name: String,
    val description: String,
    val origin: String,
    val weather: String,
    val format: String,
    val price: Int,
    val stock: Int = 0,
    var quantity: Int = 0
)