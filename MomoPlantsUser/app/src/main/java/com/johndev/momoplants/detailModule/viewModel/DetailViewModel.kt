package com.johndev.momoplants.detailModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.detailModule.model.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private var _plantEntity = MutableLiveData<PlantEntity>()
    val plantEntity: LiveData<PlantEntity> = _plantEntity

    fun onSearchPlantRealtime(idPlant: String) {
        detailsRepository.onSearchPlantRealtime(
            idPlant = idPlant,
            onSearchSuccess = { searchPlantRealtimeSuccess(it) }
        )
    }

    fun searchPlantRealtimeSuccess(plantEntity: PlantEntity?) {
        plantEntity?.let { _plantEntity.value = it }
    }

    fun onSave(plantEntity: PlantEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.addPlant(plantEntity)
        }
    }

}