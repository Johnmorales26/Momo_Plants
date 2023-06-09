package com.johndev.momoplants.mainModule.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.adapter.PlantAdapter
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataSource: MomoPlantsDataSource
) : ViewModel() {

    lateinit var firestoreListener: ListenerRegistration

    private val tag = "HomeViewModel"

    private var _plantSelected = MutableLiveData<PlantEntity?>()
    val plantSelected: LiveData<PlantEntity?> = _plantSelected

    fun onPlantSelected(plantEntity: PlantEntity?) {
        _plantSelected.value = plantEntity
    }

    fun getPlantSelected(): PlantEntity? = plantSelected.value

    fun configFirestoreRealtime(context: Context, plantAdapter: PlantAdapter) {
        val db = FirebaseFirestore.getInstance()
        val plantRef = db.collection(Constants.COLL_PLANTS)
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Toast.makeText(context, "Error al consultar datos", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val plant = snapshot.document.toObject(PlantEntity::class.java)
                plant.plantId = snapshot.document.id
                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> plantAdapter.add(plant)
                    //DocumentChange.Type.MODIFIED -> plantAdapter.update(plant)
                    //DocumentChange.Type.REMOVED -> plantAdapter.delete(plant)
                    else -> {}
                }
            }
        }
    }

    fun onSave(plantEntity: PlantEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.getPlantByID(plantEntity.plantId) {
                if (it != null) {
                    //  Update Plant
                    updatePlant(it)
                } else {
                    //  Add Plant
                    addPlant(plantEntity)
                }
            }

        }
    }

    private fun updatePlant(plantEntity: PlantEntity) {
        plantEntity.quantity += 1
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.updatePlant(plantEntity)
        }
    }

    private fun addPlant(plantEntity: PlantEntity) {
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