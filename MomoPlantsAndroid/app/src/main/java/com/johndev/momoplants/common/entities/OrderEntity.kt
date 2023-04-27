package com.johndev.momoplants.common.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "OrderEntity",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            childColumns = ["user_id"],
            parentColumns = ["user_id"],
            onDelete = CASCADE
        )
    ]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val order_id: Int = 0,
    val creationDate: Long = System.currentTimeMillis(),
    val state: String,
    val shippingAddress: String,
    val user_id: Int
)
