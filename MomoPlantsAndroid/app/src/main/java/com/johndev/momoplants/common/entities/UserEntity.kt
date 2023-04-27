package com.johndev.momoplants.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity", indices = [Index(value = ["user_id"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var user_id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val direction: String
)
