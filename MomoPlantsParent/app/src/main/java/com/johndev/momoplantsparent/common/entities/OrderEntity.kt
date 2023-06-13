package com.johndev.momoplantsparent.common.entities

import com.google.firebase.firestore.Exclude

data class OrderEntity(
    @get:Exclude var id: String = "",
    var clientId: String = "",
    var products: Map<String, PlantOrderEntity> = hashMapOf(),
    var totalPrice: Double = 0.0,
    var status: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as OrderEntity
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}