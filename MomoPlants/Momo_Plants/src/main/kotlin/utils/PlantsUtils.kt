package utils

import dataAccess.PlantsDatabase

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

}