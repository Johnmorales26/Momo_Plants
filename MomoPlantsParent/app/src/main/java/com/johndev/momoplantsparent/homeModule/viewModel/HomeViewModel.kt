package com.johndev.momoplantsparent.homeModule.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplantsparent.adapter.PlantAdapter
import com.johndev.momoplantsparent.common.entities.PlantEntity
import com.johndev.momoplantsparent.common.utils.EventPost
import com.johndev.momoplantsparent.homeModule.model.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private var _plantSelected = MutableLiveData<PlantEntity?>()
    private val plantSelected: LiveData<PlantEntity?> = _plantSelected

    private var _msg = MutableLiveData<Int>()
    val msg: LiveData<Int> = _msg

    fun onPlantSelected(plantEntity: PlantEntity?) {
        _plantSelected.value = plantEntity
    }

    fun getPlantSelected(): PlantEntity? = plantSelected.value

    fun onUploadImage(
        plantId: String?,
        photoSelectedUri: Uri?,
        callback: (EventPost) -> Unit,
        onProgressListener: (Int) -> Unit,
        ) {
        if (plantId != null && photoSelectedUri != null) {
            homeRepository.onUploadImage(
                plantId = plantId,
                photoSelectedUri = photoSelectedUri,
                callback = { callback(it) },
                onProgressListener = { onProgressListener(it) }
            )
        }
    }

    fun configFirestoreRealtime(plantAdapter: PlantAdapter) {
        homeRepository.configFirestoreRealtime(
            onAdded = { plantAdapter.add(it) },
            onModified = { plantAdapter.update(it) },
            onRemoved = { plantAdapter.delete(it) },
            onError = { _msg.value = it }
        )
    }

    fun onSave(
        plantEntity: PlantEntity,
        documentId: String,
        onComplete: () -> Unit
    ) {
        homeRepository.onSave(
            plantEntity = plantEntity,
            documentId = documentId,
            onSuccess = { _msg.value = it },
            onFailure = { _msg.value = it },
            onComplete = { onComplete() }
        )
    }

    fun onUpdate(plantEntity: PlantEntity, onComplete: () -> Unit) {
        homeRepository.onUpdate(
            plantEntity = plantEntity,
            onSuccess = { _msg.value = it },
            onFailure = { _msg.value = it },
            onComplete = { onComplete() }
        )
    }

    fun onDelete(plantEntity: PlantEntity) {
        homeRepository.onDelete(
            plantEntity = plantEntity,
            onFailure = { _msg.value = it }
        )
    }

    fun removeListener() {
        homeRepository.onRemoveListener()
    }

}