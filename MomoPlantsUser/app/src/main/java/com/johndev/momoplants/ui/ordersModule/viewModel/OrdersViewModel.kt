package com.johndev.momoplants.ui.ordersModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.ui.ordersModule.model.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    private val _ordersList = MutableLiveData<MutableList<OrderEntity>>()
    val orderList: LiveData<MutableList<OrderEntity>> = _ordersList

    private val _orderAdded = MutableLiveData<OrderEntity>()
    val orderAdded: LiveData<OrderEntity> = _orderAdded

    private val _orderModified = MutableLiveData<OrderEntity>()
    val orderModified: LiveData<OrderEntity> = _orderModified

    private val _orderRemoved = MutableLiveData<OrderEntity>()
    val orderRemoved: LiveData<OrderEntity> = _orderRemoved

    private var _status = MutableLiveData<Notification>()
    val status: LiveData<Notification> = _status

    private var _msg = MutableLiveData<Int>()
    val msg: LiveData<Int> = _msg

    fun onSetupFirestoreRealtime() {
        ordersRepository.onSetupFirestoreRealtime(
            onError = { _msg.value = it },
            onAdded = { setupFirestoreRealtimeAdded(it) },
            onModified = { setupFirestoreRealtimeModified(it) },
            onRemoved = { setupFirestoreRealtimeRemoved(it) },
            onUpdateStatus = { _status.value = it }
        )
    }

    fun setupFirestoreRealtimeRemoved(orderEntity: OrderEntity?) {
        orderEntity?.let { _orderRemoved.value = it }
    }

    fun setupFirestoreRealtimeModified(orderEntity: OrderEntity?) {
        orderEntity?.let { _orderModified.value = it }
    }

    fun setupFirestoreRealtimeAdded(order: OrderEntity?) {
        order?.let { _orderAdded.value = it }
    }

}