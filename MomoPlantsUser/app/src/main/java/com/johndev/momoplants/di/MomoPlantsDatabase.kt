package com.johndev.momoplants.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johndev.momoplants.common.dataAccess.PlantDao
import com.johndev.momoplants.common.entities.PlantEntity

@Database(entities = [PlantEntity::class], version = 1)
abstract class MomoPlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

}