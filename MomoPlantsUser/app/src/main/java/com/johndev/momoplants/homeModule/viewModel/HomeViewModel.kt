package com.johndev.momoplants.homeModule.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.adapters.PlantAdapter
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.FirebaseUtils
import com.johndev.momoplants.homeModule.model.HomeReposirory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeReposirory: HomeReposirory
) : ViewModel() {

    private val _addPlant = MutableLiveData<PlantEntity>()
    val addPlant: LiveData<PlantEntity> = _addPlant

    private val _updatePlant = MutableLiveData<PlantEntity>()
    val updatePlant: LiveData<PlantEntity> = _updatePlant

    private val _removedPlant = MutableLiveData<PlantEntity>()
    val removedPlant: LiveData<PlantEntity> = _removedPlant

    fun configFirestoreRealtime(onQueryError: (Int) -> Unit) {
        homeReposirory.configFirestoreRealtime(
            onQueryError = { msg -> onQueryError(msg) },
            onAddedPlant = { plant -> addedPlant(plant) },
            onModifiedPlant = { plant -> modifierPlant(plant) },
            onRemovedPlant = { plant -> removedPlant(plant) }
        )
    }

    fun removedPlant(plant: PlantEntity?) {
        plant?.let { _removedPlant.value = it }
    }

    fun modifierPlant(plant: PlantEntity?) {
        plant?.let { _updatePlant.value = it }
    }

    fun addedPlant(plant: PlantEntity?) {
        plant?.let { _addPlant.value = it }
    }

    fun onSave(plantEntity: PlantEntity) {
        viewModelScope.launch(Dispatchers.IO) { homeReposirory.onSave(plantEntity) }
    }

    fun onRemoveListener() = homeReposirory.onRemoveListener()

}