package utils

import dataAccess.PlantsDatabase
import entities.PlantEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.lang.Exception

object PlantsUtils {

    fun cleanScreen() {
        repeat(100) {
            print("\n")
        }
    }

    fun sleep() {
        Thread.sleep(0)
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