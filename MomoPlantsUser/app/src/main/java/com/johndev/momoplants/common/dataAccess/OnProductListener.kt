package com.johndev.momoplants.common.dataAccess

import com.johndev.momoplants.common.entities.PlantEntity

interface OnProductListener {

    fun onClick(plantEntity: PlantEntity)

    fun onClickAdd(plantEntity: PlantEntity)

}