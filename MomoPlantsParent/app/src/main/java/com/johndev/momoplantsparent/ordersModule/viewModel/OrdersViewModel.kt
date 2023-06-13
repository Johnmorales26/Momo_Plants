package com.johndev.momoplantsparent.ordersModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.FirebaseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firebaseUtils: FirebaseUtils
) : ViewModel() {

    private val _orderList = MutableLiveData<MutableList<OrderEntity>>()
    val orderList: LiveData<MutableList<OrderEntity>> = _orderList

    fun setupFirestore(context: Context) {
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
                _orderList.value = listOrders
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al consultar los datos", Toast.LENGTH_SHORT).show()
            }
    }

    fun onStatusChange(orderEntity: OrderEntity, context: Context) {
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.COLL_REQUESTS).document(orderEntity.id)
            .update(Constants.PROP_STATUS, orderEntity.status)
            .addOnSuccessListener {
                Toast.makeText(context, "Orden actualizada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar orden.", Toast.LENGTH_SHORT).show()
            }
    }

}