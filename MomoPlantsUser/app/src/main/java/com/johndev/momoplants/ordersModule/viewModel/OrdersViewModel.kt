package com.johndev.momoplants.ordersModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.adapters.PlantAdapter
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.FirebaseUtils
import com.johndev.momoplants.common.utils.onSetupStatusNotification
import com.johndev.momoplants.ordersModule.model.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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