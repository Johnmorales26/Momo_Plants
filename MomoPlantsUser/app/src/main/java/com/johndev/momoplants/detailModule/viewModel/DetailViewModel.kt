package com.johndev.momoplants.detailModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.Constants.COLL_CART
import com.johndev.momoplants.common.utils.Constants.COLL_PLANTS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private var _plantEntity = MutableLiveData<PlantEntity>()
    val plantEntity: LiveData<PlantEntity> = _plantEntity

    fun onSearchPlant(idPlant: String?, context: Context) {
        idPlant?.let {
            val db = FirebaseFirestore.getInstance()
            db.collection(COLL_PLANTS)
                .get()
                .addOnSuccessListener { snapshots ->
                    for (document in snapshots) {
                        val plant = document.toObject(PlantEntity::class.java)
                        plant.plantId = document.id
                        if (plant.plantId == it) {
                            _plantEntity.value = plant
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun onSave(
        plantEntity: PlantEntity,
        context: Context?,
        documentId: String,
        onComplete: () -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(COLL_CART)
            .document(documentId)
            .set(plantEntity)
            .addOnSuccessListener {
                Toast.makeText(context, "Planta Añadida", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al insertar", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener { onComplete() }
    }

}