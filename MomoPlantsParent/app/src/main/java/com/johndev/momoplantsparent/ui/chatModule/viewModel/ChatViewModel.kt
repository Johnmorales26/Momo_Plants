package com.johndev.momoplantsparent.ui.chatModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplantsparent.adapter.ChatAdapter
import com.johndev.momoplantsparent.ui.chatModule.model.ChatRepository
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.ToastType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _orderEntity = MutableLiveData<OrderEntity>()
    val orderEntity: LiveData<OrderEntity> = _orderEntity

    private val _msg = MutableLiveData<Pair<Int, ToastType>>()
    val msg: LiveData<Pair<Int, ToastType>> = _msg

    fun setupRealtimeDatabase(chatAdapter: ChatAdapter, updateScroll: () -> Unit) {
        _orderEntity.value?.let {
            chatRepository.onSetupRealtimeDatabase(
                idOrder = it.id,
                onChildAdded = {
                    chatAdapter.add(it)
                    updateScroll()
                },
                onChildChanged = { chatAdapter.update(it) },
                onChildRemoved = { chatAdapter.delete(it) },
                onCancelled = { _msg.value = it }
            )
        }
    }


    fun getOrderById(idOrder: String) {
        chatRepository.getOrderById(
            idOrder = idOrder,
            onSuccess = { _orderEntity.value = it },
            onFailure = { _msg.value = it }
        )
    }

    fun sendMessage(
        idOrder: String, message: String,
        onButtonEnable: (Boolean) -> Unit,
        onClearMessage: () -> Unit
    ) {
        chatRepository.onSendMessage(idOrder = idOrder, message = message,
            onButtonEnable = { isEnable -> onButtonEnable(isEnable) },
            onClearMessage = { onClearMessage() })
    }

    fun deleteMessage(orderId: String, messageId: String) {
        chatRepository.deleteMessage(orderId, messageId) {
            _msg.value = it
        }
    }

}