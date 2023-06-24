package com.johndev.momoplants.chatModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johndev.momoplants.adapters.ChatAdapter
import com.johndev.momoplants.chatModule.model.ChatRepository
import com.johndev.momoplants.common.entities.MessageEntity
import com.johndev.momoplants.common.entities.OrderEntity
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

    private val _lostOrder = MutableLiveData<Boolean>()
    val lostOrder: LiveData<Boolean> = _lostOrder

    fun onSetupRealtimeDatabase(chatAdapter: ChatAdapter, updateScroll: () -> Unit, onCancelled: () -> Unit) {
        _orderEntity.value?.let {
            chatRepository.onSetupRealtimeDatabase(
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

    fun onGetOrder(idOrder: String?) {
        chatRepository.onGetOrder(
            idOrder = idOrder,
            onSuccessListener = { getOrderSuccess(it) },
            onErrorListener = { getOrderError(it) }
        )
    }

    fun getOrderError(error: Boolean?) { error?.let { _lostOrder.value = it } }

    fun getOrderSuccess(orderEntity: OrderEntity?) { orderEntity?.let { _orderEntity.value = it } }

    fun onDeleteMessage(messageEntity: MessageEntity) {
        _orderEntity.value?.let {
            chatRepository.onDeleteMessage(
                orderEntity = it,
                messageEntity = messageEntity,
                onSuccess = { isSuccess -> deleteMessageSuccess(isSuccess) }
            )
        }
    }

    fun deleteMessageSuccess(success: Boolean) {
        _deleteMessage.value = success
    }

    fun onSendMessage(message: String, onSuccess: () -> Unit) {
        _orderEntity.value?.let { order ->
            chatRepository.onSendMessage(
                orderEntity = order,
                message = message,
                onEnabledButton = { sendMessageEnabled(it) },
                onSuccess = { onSuccess() }
            )
        }
    }

    fun sendMessageEnabled(isEnable: Boolean) { _enableButton.value = isEnable }

}