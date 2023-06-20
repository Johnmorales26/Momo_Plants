package com.johndev.momoplants.cartModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.momoplants.adapters.PlantCartAdapter
import com.johndev.momoplants.cartModule.model.CartRepository
import com.johndev.momoplants.common.entities.PlantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _listCart = MutableLiveData<List<PlantEntity>>(listOf())
    val listCart: LiveData<List<PlantEntity>> = _listCart

    fun getCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.onGetCartList {
                _listCart.postValue(it)
            }
        }
    }

    fun updateQuantity(
        plantEntity: PlantEntity,
        isIncrement: Boolean,
        plantCartAdapter: PlantCartAdapter
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateQuantity(
                plantEntity = plantEntity,
                isIncrement = isIncrement,
                onDeleteInAdapter = { viewModelScope.launch { deleteItem(plantCartAdapter, it) } },
                onUpdateInAdapter = { viewModelScope.launch { updateItem(plantCartAdapter, it) } },
                onDecreaseAmount = { plantEntity.quantity -= it }
            )
        }
        getCartList()
    }

    private suspend fun updateItem(plantCartAdapter: PlantCartAdapter, plantEntity: PlantEntity) =
        withContext(Dispatchers.Main) {
            plantCartAdapter.update(plantEntity)
        }

    private suspend fun deleteItem(plantCartAdapter: PlantCartAdapter, plantEntity: PlantEntity) =
        withContext(Dispatchers.Main) {
            plantCartAdapter.delete(plantEntity)
        }

    private fun clearCart() {
        _listCart.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                cartRepository.clearCart(it)
                getCartList()
            }
        }
    }

    fun onRequestOrder(
        plantCartAdapter: PlantCartAdapter,
        totalPrice: Double,
        enableUI: (Boolean) -> Unit,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        cartRepository.onRequestOrder(
            listPlants = plantCartAdapter.getPlants(),
            totalPrice = totalPrice,
            enableUI = {
                       enableUI(it)
            },
            onSuccess = {
                plantCartAdapter.clearCart()
                clearCart()
                onSuccess()
            },
            onFailure = { onFailure() }
        )
    }

}