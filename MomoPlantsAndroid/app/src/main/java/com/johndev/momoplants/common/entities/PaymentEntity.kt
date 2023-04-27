package com.johndev.momoplants.common.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "PaymentEntity",
    indices = [Index(value = ["payment_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = CASCADE
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val payment_id: Int = 0,
    val cardType: String,
    val cardNumber: String,
    val dueDate: Long,
    val securityCode: String,
    val user_id: Int
)
