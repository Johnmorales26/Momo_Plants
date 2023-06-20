package com.johndev.momoplants.trackModule.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.onSetupStatusNotification
import com.johndev.momoplants.trackModule.model.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackRepository: TrackRepository
) : ViewModel() {

    private var _orderEntity = MutableLiveData<OrderEntity?>(null)
    val orderEntity: LiveData<OrderEntity?> = _orderEntity

    private var _status = MutableLiveData<Notification>()
    val status: LiveData<Notification> = _status

    private var _msg = MutableLiveData<Int>()
    val msg: LiveData<Int> = _msg

    fun getOrderById(idOrder: String) {
        trackRepository.getOrderById(
            idOrder = idOrder,
            onSuccessOrder = { _orderEntity.value = it },
            onSuccessNotification = { _status.value = it },
            onMessage = { _msg.value = it }
        )
    }

    fun getOrderInRealtime(idOrder: String) {
        trackRepository.getOrderInRealtime(
            idOrder = idOrder,
            onSuccessOrder = { _orderEntity.value = it },
            onSuccessNotification = { _status.value = it }
        )
    }

}