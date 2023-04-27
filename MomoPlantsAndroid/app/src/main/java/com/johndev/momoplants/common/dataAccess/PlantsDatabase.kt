package com.johndev.momoplants.common.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.entities.PaymentEntity
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.entities.UserEntity

@Database(entities = [OrderEntity::class, PaymentEntity::class, PlantEntity::class, UserEntity::class], version = 1)
abstract class PlantsDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}