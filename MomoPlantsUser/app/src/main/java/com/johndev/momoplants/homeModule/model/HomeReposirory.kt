package com.johndev.momoplants.homeModule.model

import android.content.Context
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.R
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.FirebaseUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomeReposirory @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataSource: MomoPlantsDataSource,
    private val database: FirebaseUtils,
    private val analytics: FirebaseAnalytics
) {

    private lateinit var firestoreListener: ListenerRegistration
    private val tag = "HomeRepository"

    fun configFirestoreRealtime(
        onQueryError: (Int) -> Unit,
        onAddedPlant: (PlantEntity) -> Unit,
        onModifiedPlant: (PlantEntity) -> Unit,
        onRemovedPlant: (PlantEntity) -> Unit,
    ) {
        val plantRef = database.getPlantsRef()
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                onQueryError(R.string.msg_query_error)
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val plant = snapshot.document.toObject(PlantEntity::class.java)
                plant.plantId = snapshot.document.id
                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> onAddedPlant(plant)
                    DocumentChange.Type.MODIFIED -> onModifiedPlant(plant)
                    DocumentChange.Type.REMOVED -> onRemovedPlant(plant)
                    else -> {}
                }
            }
        }
    }

    suspend fun onSave(plantEntity: PlantEntity) {
        dataSource.getPlantByID(plantEntity.plantId) {
            if (it != null) {
                runBlocking { updatePlant(it) }
            } else {
                runBlocking { addPlant(plantEntity) }
            }
        }
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, plantEntity.plantId)
            param(FirebaseAnalytics.Param.ITEM_NAME, plantEntity.name!!)
        }
    }

    private suspend fun updatePlant(plantEntity: PlantEntity) {
        plantEntity.quantity += 1
        dataSource.updatePlant(plantEntity)
    }

    private suspend fun addPlant(plantEntity: PlantEntity) {
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

    fun onRemoveListener() = firestoreListener.remove()

}