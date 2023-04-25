package com.johndev.momoplants.entities

data class UserEntity(
    var id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val direction: String
)
