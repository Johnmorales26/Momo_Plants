package com.johndev.momoplantsparent.ordersModule.model

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.FirebaseUtils
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val firebaseUtils: FirebaseUtils
) {

    fun setupFirestore(
        onSuccess: (MutableList<OrderEntity>) -> Unit,
        onFailure: (Int) -> Unit
    ) {
        val listOrders = mutableListOf<OrderEntity>()
        val user = FirebaseAuth.getInstance().currentUser
        firebaseUtils.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    user?.let {
                        order.id = document.id
                        listOrders.add(order)
                    }
                }
                onSuccess(listOrders)
            }
            .addOnFailureListener {
                onFailure(R.string.msg_query_error)
            }
    }

    fun onStatusChange(
        orderEntity: OrderEntity,
        onSuccess: (Int) -> Unit,
        onFailure: (Int) -> Unit
    ) {
        firebaseUtils.getRequestsRef().document(orderEntity.id)
            .update(Constants.PROP_STATUS, orderEntity.status)
            .addOnSuccessListener {
                onSuccess(R.string.msg_order_update)
            }
            .addOnFailureListener {
                onFailure(R.string.msg_error_order_update)
            }
    }

}