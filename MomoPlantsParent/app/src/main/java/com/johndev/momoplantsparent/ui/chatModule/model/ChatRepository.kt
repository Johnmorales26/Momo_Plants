package com.johndev.momoplantsparent.ui.chatModule.model

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.MessageEntity
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.FirebaseUtils
import com.johndev.momoplantsparent.common.utils.ToastType
import org.checkerframework.checker.units.qual.UnitsRelations
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val database: FirebaseUtils,
) {

    private fun getMessage(snapshot: DataSnapshot): MessageEntity? {
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

    fun onSetupRealtimeDatabase(
        idOrder: String, onChildAdded: (MessageEntity) -> Unit,
        onChildChanged: (MessageEntity) -> Unit,
        onChildRemoved: (MessageEntity) -> Unit,
        onCancelled: (Pair<Int, ToastType>) -> Unit
    ) {
        val database = Firebase.database
        val chatRef = database.getReference(Constants.PATH_CHATS).child(idOrder)
        val childListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                getMessage(snapshot)?.let {
                    onChildAdded(it)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                getMessage(snapshot)?.let { message ->
                    onChildChanged(message)
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                getMessage(snapshot)?.let { message ->
                    onChildRemoved(message)
                }
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                onCancelled(Pair(R.string.chat_error_load, ToastType.Error))
            }
        }
        chatRef.addChildEventListener(childListener)
    }

    fun getOrderById(idOrder: String?, onSuccess: (OrderEntity) -> Unit, onFailure: (Pair<Int, ToastType>) -> Unit) {
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    order.id = document.id
                    if (idOrder == order.id) {
                        onSuccess(order)
                    }
                }
            }
            .addOnFailureListener {
                onFailure(Pair(R.string.chat_order_not_found, ToastType.Error))
            }
    }

    fun onSendMessage(
        idOrder: String, message: String,
        onButtonEnable: (Boolean) -> Unit,
        onClearMessage: () -> Unit
    ) {
        val database = Firebase.database
        val chatRef = database.getReference(Constants.PATH_CHATS).child(idOrder)
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val message = MessageEntity(
                message = message,
                sender = it.uid,
                time = System.currentTimeMillis().toString().trim()
            )
            onButtonEnable(false)
            chatRef.push().setValue(message)
                .addOnSuccessListener {
                    onClearMessage()
                }
                .addOnCompleteListener {
                    onButtonEnable(true)
                }
        }
    }

    fun deleteMessage(
        idOrder: String,
        messageId: String,
        callback: (Pair<Int, ToastType>) -> Unit
    ) {
        val database = Firebase.database
        val messageRef =
            database.getReference(Constants.PATH_CHATS).child(idOrder).child(messageId)
        messageRef.removeValue { error, ref ->
            if (error != null) {
                callback(Pair(R.string.message_error_delete_message, ToastType.Error))
            } else {
                callback(Pair(R.string.message_success_delete_message, ToastType.Success))
            }
        }
    }

}