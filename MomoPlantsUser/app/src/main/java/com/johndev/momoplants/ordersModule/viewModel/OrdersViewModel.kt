package com.johndev.momoplants.ordersModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.adapters.PlantAdapter
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.FirebaseUtils
import com.johndev.momoplants.common.utils.onSetupStatusNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val database: FirebaseUtils
): ViewModel() {

    private lateinit var firestoreListener: ListenerRegistration

    private val _ordersList = MutableLiveData<MutableList<OrderEntity>>()
    val orderList: LiveData<MutableList<OrderEntity>> = _ordersList

    private var _status = MutableLiveData<Notification>()
    val status: LiveData<Notification> = _status

    fun onSetupFirestore() {
        val orders: MutableList<OrderEntity> = mutableListOf()
        val user = FirebaseAuth.getInstance().currentUser
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    user?.let {
                        if (user.uid == order.clientId) {
                            order.id = document.id
                            orders.add(order)
                            //orderAdapter.add(order)
                        }
                    }
                }
                _ordersList.value = orders
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al consultar los datos", Toast.LENGTH_SHORT).show()
            }
    }

    fun onSetupFirestoreRealtime(orderAdapter: OrderAdapter) {
        val plantRef = database.getRequestsRef()
        val user = FirebaseAuth.getInstance().currentUser
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Toast.makeText(context, "Error al consultar datos", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val order = snapshot.document.toObject(OrderEntity::class.java)

                user?.let {
                    if (user.uid == order.clientId) {
                        order.id = snapshot.document.id
                        _status.value = Notification(order.id, onSetupStatusNotification(context, order.status))
                        when (snapshot.type) {
                            DocumentChange.Type.ADDED -> orderAdapter.add(order)
                            DocumentChange.Type.MODIFIED -> orderAdapter.update(order)
                            DocumentChange.Type.REMOVED -> orderAdapter.delete(order)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

}