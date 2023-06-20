package com.johndev.momoplantsparent.ordersModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.ordersModule.model.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    private val _orderList = MutableLiveData<MutableList<OrderEntity>>()
    val orderList: LiveData<MutableList<OrderEntity>> = _orderList

    private val _msg = MutableLiveData<Int>()
    val msg: LiveData<Int> = _msg

    fun setupFirestore() {
        ordersRepository.setupFirestore(
            onSuccess = { listOrders -> _orderList.value = listOrders },
            onFailure = { _msg.value = it }
        )
    }

    fun statusChange(orderEntity: OrderEntity, context: Context) {
        ordersRepository.onStatusChange(
            orderEntity = orderEntity,
            onSuccess = { _msg.value = it },
            onFailure = { _msg.value = it }
        )
    }

}