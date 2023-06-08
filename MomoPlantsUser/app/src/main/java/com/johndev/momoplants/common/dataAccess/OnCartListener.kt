package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.PlantEntity

interface OnCartListener {

    fun incrementQuantity(plantEntity: PlantEntity)
    fun decrementQuantity(plantEntity: PlantEntity)

    fun showTotal(total: Double)

}