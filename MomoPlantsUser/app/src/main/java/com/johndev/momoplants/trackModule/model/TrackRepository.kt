package com.johndev.momoplants.trackModule.model

import android.content.Context
import android.widget.Toast
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.FirebaseUtils
import com.johndev.momoplants.common.utils.onSetupStatusNotification
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TrackRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private var database: FirebaseUtils
) {

    fun getOrderById(
        idOrder: String,
        onMessage: (Int) -> Unit,
        onSuccessOrder: (OrderEntity) -> Unit,
        onSuccessNotification: (Notification) -> Unit
    ) {
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    order.id = document.id
                    if (idOrder == order.id) {
                        onSuccessOrder(order)
                        onSuccessNotification(
                            Notification(
                                order.id,
                                onSetupStatusNotification(context, order.status)
                            )
                        )
                        onMessage(R.string.chat_order_found)
                    }
                }
            }
            .addOnFailureListener {
                onMessage(R.string.chat_order_not_found)
            }
            .addOnCompleteListener {}
    }

    fun getOrderInRealtime(
        idOrder: String,
        onSuccessOrder: (OrderEntity) -> Unit,
        onSuccessNotification: (Notification) -> Unit
    ) {
        val orderRef = database.getRequestsRef().document(idOrder)
        orderRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val order = snapshot.toObject(OrderEntity::class.java)
                order?.let {
                    it.id = snapshot.id
                    onSuccessNotification(Notification(order.id, onSetupStatusNotification(context, order.status)))
                    onSuccessOrder(it)
                }
            }
        }
    }

}