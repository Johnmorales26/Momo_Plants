package com.johndev.momoplantsparent.ordersModule.model

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.fmc.NotificationRS
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.Constants.COLL_TOKENS
import com.johndev.momoplantsparent.common.utils.Constants.PROP_TOKEN
import com.johndev.momoplantsparent.common.utils.FirebaseUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val firebaseUtils: FirebaseUtils,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val notification: NotificationRS
) {

    private fun notifyClient(orderEntity: OrderEntity) {
        firebaseUtils.getUsersRef()
            .document(orderEntity.clientId)
            .collection(COLL_TOKENS)
            .get()
            .addOnSuccessListener { snapshot ->
                var tokensStr = ""
                for (document in snapshot) {
                    val tokenMap = document.data
                    tokensStr += "${tokenMap.getValue(PROP_TOKEN)},"
                }
                if (tokensStr.isNotEmpty()) {
                    tokensStr = tokensStr.dropLast(1)
                    var names = ""
                    orderEntity.products.forEach { names += "${it.value.name}, " }
                    names = names.dropLast(2)
                    //val index = aKeys.indexOf()
                    notification.sendNotification(
                        "Tu pedido a sido ${onSetupStatusNotification(context, orderEntity.status)}",
                        names,
                        tokensStr
                    )
                }
            }
            .addOnFailureListener {
                Log.e("Error FMC", "Error al consultar los datos")
            }
    }

    private fun onSetupStatusNotification(context: Context, status: Int): String {
        return when (status - 1) {
            0 -> context.getString(R.string.order_status_ordered)
            1 -> context.getString(R.string.order_status_preparing)
            2 -> context.getString(R.string.order_status_send)
            3 -> context.getString(R.string.order_status_delivered)
            else -> context.getString(R.string.order_status_unknown)
        }
    }

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
                notifyClient(orderEntity)
                //  Analytics
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO) {
                    val products = mutableListOf<Bundle>()
                    orderEntity.products.forEach {
                        val bundle: Bundle = Bundle()
                        bundle.putString("id_product", it.key)
                        products.add(bundle)
                    }
                    param(FirebaseAnalytics.Param.SHIPPING, products.toTypedArray())
                    param(FirebaseAnalytics.Param.PRICE, orderEntity.totalPrice)
                }
            }
            .addOnFailureListener {
                onFailure(R.string.msg_error_order_update)
            }
    }

}