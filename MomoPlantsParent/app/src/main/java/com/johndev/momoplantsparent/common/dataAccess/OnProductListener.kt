package com.johndev.momoplantsparent.common.dataAccess

import com.johndev.momoplantsparent.common.entities.PlantEntity

interface OnProductListener {

    fun onClick(plantEntity: PlantEntity)
    fun onLongClick(plantEntity: PlantEntity)

}