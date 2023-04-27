package com.johndev.momoplants.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "PlantEntity",
    indices = [Index(value = ["plant_id"], unique = true)],
)
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val plant_id: Int = 0,
    val name: String,
    val description: String,
    val image: Int,
    val origin: String,
    val weather: String,
    val format: String,
    val price: Int,
    val stock: Int = 0,
    var quantity: Int = 0
)