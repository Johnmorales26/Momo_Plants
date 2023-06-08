package com.johndev.momoplants.common.dataAccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johndev.momoplants.common.entities.PlantEntity

@Dao
interface PlantDao {

    @Query("SELECT * FROM PlantEntity")
    suspend fun getAll(): List<PlantEntity>

    @Insert
    suspend fun add(plantEntity: PlantEntity): Long

    @Update
    suspend fun update(plantEntity: PlantEntity)

    @Delete
    suspend fun delete(plantEntity: PlantEntity)

}