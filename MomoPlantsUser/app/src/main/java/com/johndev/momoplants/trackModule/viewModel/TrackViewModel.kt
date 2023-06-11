package com.johndev.momoplants.trackModule.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.FirebaseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private var database: FirebaseUtils
): ViewModel() {

    private var _orderEntity = MutableLiveData<OrderEntity>()
    val orderEntity: LiveData<OrderEntity> = _orderEntity

    private var _idOrder = MutableLiveData<String>("")
    val idOrder: LiveData<String> = _idOrder

    private var _status = MutableLiveData(0)
    val status: LiveData<Int> = _status

    fun onGetIdOrder(idOrder: String) { _idOrder.value = idOrder }

    fun onGetStatusOrder(status: Int) { _status.value = status }

    fun onGetOrder(context: Context) {
        Log.i("TrackActivity", "idOrder: ${_idOrder.value}")
        database.getRequestsRef().get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    order.id = document.id
                    if (_idOrder.value == order.id) {
                        _orderEntity.postValue(order)
                        Toast.makeText( context, "Orden encontrada", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Orden no encontrada", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {}
    }

    fun getOrderInRealtime() {
        _idOrder.value?.let {
            val orderRef = database.getRequestsRef().document()
            orderRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //Toast.makeText(this, "Error al consultar esta orden", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val order = snapshot.toObject(OrderEntity::class.java)
                    order?.let {
                        it.id = snapshot.id
                        _orderEntity.value = it
                        //updateIU(it)
                    }
                }
            }
        }
    }

}