package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.PlantEntity

interface OnCartListener {

    fun setQuantity(plantEntity: PlantEntity)

    fun showTotal(total: Double)

}