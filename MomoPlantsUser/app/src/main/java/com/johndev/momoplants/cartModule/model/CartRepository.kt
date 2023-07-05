package com.johndev.momoplants.cartModule.model

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.entities.PlantOrderEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.Constants.USER_PROP_QUANTITY
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val dataSource: MomoPlantsDataSource,
    private val analytics: FirebaseAnalytics
) {

    suspend fun onGetCartList(callback: (List<PlantEntity>?) -> Unit) {
        dataSource.getAllPlants {
            if (it.isEmpty()) {
                callback(null)
            } else {
                callback(it)
            }
        }
    }

    suspend fun updateQuantity(
        plantEntity: PlantEntity,
        isIncrement: Boolean,
        onDeleteInAdapter: (PlantEntity) -> Unit,
        onUpdateInAdapter: (PlantEntity) -> Unit,
        onDecreaseAmount: (Int) -> Unit
    ) {
        if (isIncrement) {
            plantEntity.quantity += 1
        } else {
            if (plantEntity.quantity == 1) {
                dataSource.deletePlant(plantEntity)
                onDeleteInAdapter(plantEntity)
            } else {
                onDecreaseAmount(1)
            }
        }
        dataSource.updatePlant(plantEntity)
        onUpdateInAdapter(plantEntity)
    }

    suspend fun clearCart(plantEntities: List<PlantEntity>) {
        plantEntities.forEach {
            dataSource.deletePlant(it)
        }
    }

    fun onRequestOrder(
        listPlants: List<PlantEntity>,
        totalPrice: Double,
        enableUI: (Boolean) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { myUser ->
            enableUI(false)
            val plants = hashMapOf<String, PlantOrderEntity>()
            listPlants.forEach { plant ->
                plants[plant.plantId] =
                    PlantOrderEntity(plant.plantId, plant.name!!, plant.quantity)
            }
            val order = OrderEntity(
                clientId = myUser.uid,
                products = plants,
                totalPrice = totalPrice,
                status = 1
            )
            val db = FirebaseFirestore.getInstance()
            db.collection(Constants.COLL_REQUESTS)
                .add(order)
                .addOnSuccessListener {
                    onSuccess()
                    analytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO) {
                        val products = mutableListOf<Bundle>()
                        plants.forEach {
                            if (it.value.quantity > 5) {
                                val bundle = Bundle()
                                bundle.putString("id_product", it.key)
                                products.add(bundle)
                            }
                        }
                        param(FirebaseAnalytics.Param.QUANTITY, products.toTypedArray())
                    }
                    analytics.setUserProperty(
                        USER_PROP_QUANTITY,
                        if (plants.size > 0) "con_mayoreo" else "sin_mayoreo"
                    )
                }
                .addOnFailureListener {
                    onFailure()
                }
                .addOnCompleteListener {
                    enableUI(true)
                }
        }
    }

}