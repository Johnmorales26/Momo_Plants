package com.johndev.momoplants.ui.chatModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.ChatAdapter
import com.johndev.momoplants.common.entities.MessageEntity
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.ui.chatModule.model.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _orderEntity = MutableLiveData<OrderEntity>()
    val orderEntity: LiveData<OrderEntity> = _orderEntity

    private val _deleteMessage = MutableLiveData<Boolean>()
    val deleteMessage: LiveData<Boolean> = _deleteMessage

    private val _enableButton = MutableLiveData<Boolean>()
    val enableButton: LiveData<Boolean> = _enableButton

    private val _msg = MutableLiveData<Int>()
    val msg: LiveData<Int> = _msg

    fun setupRealtimeDatabase(chatAdapter: ChatAdapter, updateScroll: () -> Unit, onCancelled: () -> Unit) {
        _orderEntity.value?.let {
            chatRepository.setupRealtimeDatabase(
                orderEntity = it,
                onChildAdded = { message ->
                    chatAdapter.add(message)
                    updateScroll()
                },
                onChildChanged = { message -> chatAdapter.update(message) },
                onChildRemoved = { message -> chatAdapter.delete(message) },
                onCancelled = { onCancelled() }
            )
        }
    }

    fun getOrder(idOrder: String?) {
        chatRepository.onGetOrder(
            idOrder = idOrder,
            callback = { getOrderResult(it) }
        )
    }

    fun getOrderById(idOrder: String) {
        chatRepository.getOrderById(
            idOrder = idOrder,
            onSuccess = { _orderEntity.value = it },
            onFailure = { /*_msg.value = it*/ }
        )
    }

    private fun getOrderResult(orderEntity: OrderEntity?) {
        orderEntity?.let {
            _orderEntity.value = it
        } ?: {
            _msg.value = R.string.chat_order_not_found
        }
    }

    fun deleteMessage(messageEntity: MessageEntity) {
        _orderEntity.value?.let {
            chatRepository.deleteMessage(
                orderEntity = it,
                messageEntity = messageEntity,
                callback = { isSuccess -> deleteMessageResult(isSuccess) }
            )
        }
    }

    private fun deleteMessageResult(success: Boolean) {
        _deleteMessage.value = success
    }

    fun onSendMessage(message: String) {
        _orderEntity.value?.let { order ->
            chatRepository.sendMessage(
                orderEntity = order,
                message = message,
                callback = { sendMessageResult(it) },
            )
        }
    }

    private fun sendMessageResult(isEnable: Boolean) { _enableButton.value = isEnable }

}