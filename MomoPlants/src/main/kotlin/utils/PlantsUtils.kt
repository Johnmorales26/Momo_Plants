package utils

import dataAccess.PlantsDatabase
import entities.PlantEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object PlantsUtils {

    fun cleanScreen() {
        repeat(2000) {
            print("\n")
        }
    }

    fun sleep() {
        Thread.sleep(2000)
    }

    fun showPlantsCatalogue() {
        PlantsDatabase.getAllPlants().forEachIndexed { index, plant ->
            println("Id: $index, ${plant.stock} x ${plant.name} - Precio unitario: ${plant.price}")
        }
    }

    fun getAllPlantsByFlow(): Flow<PlantEntity> {
        return flow {
            PlantsDatabase.getAllPlants().forEach {
                emit(it)
            }
        }
    }

}