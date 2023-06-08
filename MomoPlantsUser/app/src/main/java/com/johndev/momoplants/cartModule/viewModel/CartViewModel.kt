package com.johndev.momoplants.cartModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.momoplants.adapter.PlantCartAdapter
import com.johndev.momoplants.common.dataAccess.MomoPlantsDataSource
import com.johndev.momoplants.common.entities.PlantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private var dataSource: MomoPlantsDataSource
) : ViewModel() {

    private var _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    private val _listCart = MutableLiveData<List<PlantEntity>>(listOf())
    val listCart: LiveData<List<PlantEntity>> = _listCart

    fun getCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.getAllPlants {
                onGetTotalPrice(it)
                _listCart.postValue(it)
            }
        }
    }

    private fun onGetTotalPrice(listPlants: List<PlantEntity>) {
        viewModelScope.launch(Dispatchers.Main) {
            var totalPrice = 0.0
            listPlants.forEach { totalPrice += (it.price * it.quantity) }
            _totalPrice.value = totalPrice
        }
    }

    fun updateQuantity(
        plantEntity: PlantEntity,
        isIncrement: Boolean,
        plantCartAdapter: PlantCartAdapter
    ) {
        if (isIncrement) {
            plantEntity.quantity += 1
        } else {
            if (plantEntity.quantity == 1) {
                viewModelScope.launch(Dispatchers.IO) { dataSource.deletePlant(plantEntity) }
                plantCartAdapter.delete(plantEntity)
            } else {
                plantEntity.quantity -= 1
            }
        }
        viewModelScope.launch(Dispatchers.IO) { dataSource.updatePlant(plantEntity) }
        plantCartAdapter.update(plantEntity)
        getCartList()
    }

}