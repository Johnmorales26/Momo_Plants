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

    fun onGetOrder(idOrder: String?, onError: () -> Unit) {
        chatRepository.onGetOrder(
            idOrder = idOrder,
            onSuccessListener = { _orderEntity.value = it },
            onErrorListener = { onError() }
        )
    }

    fun onDeleteMessage(messageEntity: MessageEntity, onSuccess: (Boolean) -> Unit) {
        _orderEntity.value?.let {
            chatRepository.onDeleteMessage(
                orderEntity = it,
                messageEntity = messageEntity,
                onSuccess = { isSuccess ->
                    onSuccess(isSuccess)
                }
            )
        }
    }

    fun onSendMessage(message: String, onEnabledButton: (Boolean) -> Unit, onSuccess: () -> Unit) {
        _orderEntity.value?.let { order ->
            chatRepository.onSendMessage(
                orderEntity = order,
                message = message,
                onEnabledButton = { onEnabledButton(it) },
                onSuccess = { onSuccess() }
            )
        }
    }

}