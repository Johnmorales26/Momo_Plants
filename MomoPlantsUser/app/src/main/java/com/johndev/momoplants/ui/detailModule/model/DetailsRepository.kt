package com.johndev.momoplants.ui.detailModule.model

import android.util.Log
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.FirebaseUtils
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private var dataSource: MomoPlantsDataSource,
    private val database: FirebaseUtils
) {

    private val tag = "DetailRepository"

    fun onSearchPlantRealtime(idPlant: String, onSearchSuccess: (PlantEntity) -> Unit) {
        val orderRef = database.getPlantsRef().document(idPlant)
        orderRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val plant = snapshot.toObject(PlantEntity::class.java)
                plant?.let {
                    it.plantId = snapshot.id
                    onSearchSuccess(it)
                }
            }
        }
    }

    suspend fun updatePlant(plantEntity: PlantEntity) {
        plantEntity.quantity += 1
        dataSource.updatePlant(plantEntity)
    }

    suspend fun addPlant(plantEntity: PlantEntity) {
        val cartPlant = plantEntity.copy()
        cartPlant.quantity = 1
        dataSource.addPlant(cartPlant) { id ->
            if (id < 1) {
                Log.i(tag, "Save Plant: Error!!")
            } else {
                Log.i(tag, "Save Plant: Success!!")
            }
        }
    }

}