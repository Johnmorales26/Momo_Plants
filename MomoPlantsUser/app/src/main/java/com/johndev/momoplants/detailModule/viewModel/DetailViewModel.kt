package com.johndev.momoplants.detailModule.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.COLL_PLANTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var dataSource: MomoPlantsDataSource
) : ViewModel() {

    private val tag = "DetailViewModel"

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

    fun onSave(plantEntity: PlantEntity) {
        val cartPlant = plantEntity.copy()
        cartPlant.quantity = 1
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.addPlant(cartPlant) { id ->
                if (id < 1) {
                    Log.i(tag, "Save Plant: Error!!")
                } else {
                    Log.i(tag, "Save Plant: Success!!")
                }
            }
        }
    }

}