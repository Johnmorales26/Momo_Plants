package com.johndev.momoplants.ordersModule.model

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.FirebaseUtils
import com.johndev.momoplants.common.utils.onSetupStatusNotification
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val database: FirebaseUtils
) {

    private lateinit var firestoreListener: ListenerRegistration

    fun onSetupFirestore(onSuccess: (MutableList<OrderEntity>) -> Unit,
                         onFailure: (Int) -> Unit) {
        val orders = mutableListOf<OrderEntity>()
        val user = FirebaseAuth.getInstance().currentUser
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    user?.let {
                        if (user.uid == order.clientId) {
                            order.id = document.id
                            orders.add(order)
                        }
                    }
                }
                onSuccess(orders)
            }
            .addOnFailureListener {
                onFailure(R.string.msg_query_error)
            }
    }

    fun onSetupFirestoreRealtime(
        onError: (Int) -> Unit,
        onAdded: (OrderEntity) -> Unit,
        onModified: (OrderEntity?) -> Unit,
        onRemoved: (OrderEntity?) -> Unit,
        onUpdateStatus: (Notification?) -> Unit
    ) {
        val plantRef = database.getRequestsRef()
        val user = FirebaseAuth.getInstance().currentUser
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                onError(R.string.msg_query_error)
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val order = snapshot.document.toObject(OrderEntity::class.java)

                user?.let {
                    if (user.uid == order.clientId) {
                        order.id = snapshot.document.id
                        onUpdateStatus(Notification(order.id, onSetupStatusNotification(context, order.status)))
                        when (snapshot.type) {
                            DocumentChange.Type.ADDED -> onAdded(order)
                            DocumentChange.Type.MODIFIED -> onModified(order)
                            DocumentChange.Type.REMOVED -> onRemoved(order)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

}