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

    fun configFirestoreRealtime(plantAdapter: PlantAdapter, onQueryError: (Int) -> Unit) {
        homeReposirory.configFirestoreRealtime(
            onQueryError = { msg -> onQueryError(msg) },
            onAddedPlant = { plant ->
                plantAdapter.add(plant)
            }
        )
    }

    fun onSave(plantEntity: PlantEntity) {
        viewModelScope.launch(Dispatchers.IO) { homeReposirory.onSave(plantEntity) }
    }

    fun onRemoveListener() = homeReposirory.onRemoveListener()

}