package com.johndev.momoplants.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "PlantEntity", indices = [Index(value = ["plantId"], unique = true)])
data class PlantEntity(
    @PrimaryKey @get:Exclude var plantId: String = "",
    var name: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var origin: String? = null,
    var weather: String? = null,
    var format: String? = null,
    var price: Double = 0.0,
    var quantity: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PlantEntity
        if (plantId != other.plantId) return false
        return true
    }

    override fun hashCode(): Int {
        return plantId.hashCode() ?: 0
    }
}