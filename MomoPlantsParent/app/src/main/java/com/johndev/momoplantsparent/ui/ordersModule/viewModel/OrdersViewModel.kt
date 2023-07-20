package com.johndev.momoplantsparent.ui.ordersModule.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.ToastType
import com.johndev.momoplantsparent.ui.ordersModule.model.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    private val _orderList = MutableLiveData<MutableList<OrderEntity>>()
    val orderList: LiveData<MutableList<OrderEntity>> = _orderList

    private val _msg = MutableLiveData<Pair<Int, ToastType>>()
    val msg: LiveData<Pair<Int, ToastType>> = _msg

    fun setupFirestore() {
        ordersRepository.setupFirestore(
            callback = { listOrders ->
                if (listOrders != null) {
                    _orderList.value = listOrders
                } else {
                    _msg.value = Pair(R.string.msg_query_error, ToastType.Error)
                }
            }
        )
    }

    fun statusChange(orderEntity: OrderEntity, context: Context) {
        ordersRepository.onStatusChange(
            orderEntity = orderEntity) {
            _msg.value = it
        }
    }

}