package com.johndev.momoplants.cartModule.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.johndev.momoplants.adapter.PlantCartAdapter
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    lateinit var firestoreListener: ListenerRegistration

    private var _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    fun configFirestoreRealtime(context: Context, plantAdapter: PlantCartAdapter) {
        val db = FirebaseFirestore.getInstance()
        val plantRef = db.collection(Constants.COLL_CART)
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Toast.makeText(context, "Error al consultar datos", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val plant = snapshot.document.toObject(PlantEntity::class.java)
                plant.plantId = snapshot.document.id
                val accumulator = _totalPrice.value ?: 0.0
                _totalPrice.value = accumulator + plant.price * plant.quantity
                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> plantAdapter.add(plant)
                    DocumentChange.Type.MODIFIED -> plantAdapter.update(plant)
                    DocumentChange.Type.REMOVED -> plantAdapter.delete(plant)
                }
            }
        }
    }

}