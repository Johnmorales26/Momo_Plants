package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.PlantEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MomoPlantsDataSourceRoom @Inject constructor(
    private val dao: PlantDao
) : MomoPlantsDataSource {

    override suspend fun getAllPlants(callback: (List<PlantEntity>) -> Unit) = callback(dao.getAll())

    override suspend fun addPlant(plantEntity: PlantEntity, callback: (Long) -> Unit) = callback(dao.add(plantEntity))

    override suspend fun deletePlant(plantEntity: PlantEntity) = dao.delete(plantEntity)

    override suspend fun updatePlant(plantEntity: PlantEntity) = dao.update(plantEntity)
}