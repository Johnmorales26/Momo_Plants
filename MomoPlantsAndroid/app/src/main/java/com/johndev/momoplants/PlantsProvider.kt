package com.johndev.momoplants

import com.johndev.momoplants.common.entities.PlantEntity

class PlantsProvider {
    companion object{
        val PlantList = listOf<PlantEntity>(
            PlantEntity(plant_id = 1,
                name = "Sanseviera",
                description = "hola",
                image = 1,
                origin = "mar",
                weather = "si",
                format = "si",
                price = 12,
                stock = 2,
                quantity = 2
            )
        )
    }
}