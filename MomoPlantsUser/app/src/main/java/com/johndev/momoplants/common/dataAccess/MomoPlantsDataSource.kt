package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.PlantEntity

interface MomoPlantsDataSource {

    suspend fun getAllPlants(callback: (List<PlantEntity>) -> Unit)
    suspend fun addPlant(plantEntity: PlantEntity, callback: (Long) -> Unit)
    suspend fun deletePlant(plantEntity: PlantEntity)
    suspend fun updatePlant(plantEntity: PlantEntity)

}