package com.johndev.momoplants.chatModule.model

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.johndev.momoplants.common.entities.MessageEntity
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.FirebaseUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChatRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val database: FirebaseUtils
) {

    fun onSetupRealtimeDatabase(
        orderEntity: OrderEntity,
        onChildAdded: (MessageEntity) -> Unit,
        onChildChanged: (MessageEntity) -> Unit,
        onChildRemoved: (MessageEntity) -> Unit,
        onCancelled: () -> Unit,
        ) {
            val database = Firebase.database
            val chatRef = database.getReference(Constants.PATH_CHATS).child(orderEntity.id)
            val childListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    onGetMessage(snapshot)?.let { message -> onChildAdded(message) }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    onGetMessage(snapshot)?.let { message -> onChildChanged(message) }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    onGetMessage(snapshot)?.let { message -> onChildRemoved(message) }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) { onCancelled() }
            }
            chatRef.addChildEventListener(childListener)
    }

    fun onGetMessage(snapshot: DataSnapshot): MessageEntity? {
        snapshot.getValue(MessageEntity::class.java)?.let { message ->
            snapshot.key?.let {
                message.id = it
            }
            FirebaseAuth.getInstance().currentUser?.let { user ->
                message.myUid = user.uid
            }
            return message
        }
        return null
    }

    fun onGetOrder(
        idOrder: String?,
        onSuccessListener: (OrderEntity?) -> Unit,
        onErrorListener: (Boolean?) -> Unit
    ) {
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    order.id = document.id
                    if (idOrder == order.id) {
                        onSuccessListener(order)
                    }
                }
            }
            .addOnFailureListener {
                onErrorListener(true)
            }
    }

    fun onSendMessage(orderEntity: OrderEntity, message: String, onEnabledButton: (Boolean) -> Unit, onSuccess: () -> Unit) {
            val database = Firebase.database
            val chatRef = database.getReference(Constants.PATH_CHATS).child(orderEntity.id)
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val messageEntity = MessageEntity(
                    message = message,
                    sender = it.uid,
                    time = System.currentTimeMillis().toString().trim()
                )
                onEnabledButton(false)
                chatRef.push().setValue(messageEntity)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnCompleteListener {
                        onEnabledButton(true)
                    }
            }
    }

    fun onDeleteMessage(
        orderEntity: OrderEntity,
        messageEntity: MessageEntity,
        onSuccess: (Boolean) -> Unit
    ) {
        val database = Firebase.database
        val messageRef =
            database.getReference(Constants.PATH_CHATS).child(orderEntity.id)
                .child(messageEntity.id)
        messageRef.removeValue { error, _ ->
            if (error != null) onSuccess(false) else onSuccess(true)
        }
    }

}