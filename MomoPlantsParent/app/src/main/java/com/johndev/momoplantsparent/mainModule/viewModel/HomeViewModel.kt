package com.johndev.momoplantsparent.mainModule.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.johndev.momoplantsparent.adapter.PlantAdapter
import com.johndev.momoplantsparent.common.entities.PlantEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.EventPost

class HomeViewModel : ViewModel() {

    lateinit var firestoreListener: ListenerRegistration

    private var _plantSelected = MutableLiveData<PlantEntity?>()
    val plantSelected: LiveData<PlantEntity?> = _plantSelected

    fun onPlantSelected(plantEntity: PlantEntity?) {
        _plantSelected.value = plantEntity
    }

    fun getPlantSelected(): PlantEntity? = plantSelected.value

    fun onUploadImage(
        plantId: String?,
        photoSelectedUri: Uri?,
        context: Context,
        callback: (EventPost) -> Unit,
        onProgressListener: (Int) -> Unit,
        ) {
        val eventPost = EventPost()
        eventPost.documentId = plantId ?: FirebaseFirestore.getInstance()
            .collection(Constants.COLL_PLANTS).document().id
        val storageRef = FirebaseStorage.getInstance().reference.child(Constants.PATH_PLANT_IMAGE)
        photoSelectedUri?.let { uri ->
            val photoRef = storageRef.child(eventPost.documentId!!)
            photoRef.putFile(uri)
                .addOnProgressListener {
                    onProgressListener((100 * it.bytesTransferred / it.totalByteCount).toInt())
                }
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                        eventPost.apply {
                            isSuccess = true
                            photoUrl = downloadUrl.toString()
                        }
                        callback(eventPost)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al cargar imagen", Toast.LENGTH_SHORT).show()
                    eventPost.isSuccess = false
                    callback(eventPost)
                }
        }
    }

    fun configFirestoreRealtime(context: Context, plantAdapter: PlantAdapter) {
        val db = FirebaseFirestore.getInstance()
        val plantRef = db.collection(Constants.COLL_PLANTS)
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Toast.makeText(context, "Error al consultar datos", Toast.LENGTH_SHORT)
                    .show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val plant = snapshot.document.toObject(PlantEntity::class.java)
                plant.plantId = snapshot.document.id
                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> plantAdapter.add(plant)
                    DocumentChange.Type.MODIFIED -> plantAdapter.update(plant)
                    DocumentChange.Type.REMOVED -> plantAdapter.delete(plant)
                }
            }
        }
    }

    fun onSave(
        plantEntity: PlantEntity,
        context: Context?,
        documentId: String,
        onComplete: () -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.COLL_PLANTS)
            .document(documentId)
            .set(plantEntity)
            .addOnSuccessListener {
                Toast.makeText(context, "Planta Añadida", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al insertar", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener { onComplete() }
    }

    fun onUpdate(plantEntity: PlantEntity, context: Context?, onComplete: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        plantEntity.plantId?.let { id ->
            db.collection(Constants.COLL_PLANTS)
                .document(id)
                .set(plantEntity)
                .addOnSuccessListener {
                    Toast.makeText(context, "Planta actualizada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener { onComplete() }
        }
    }

    fun onDelete(plantEntity: PlantEntity, context: Context) {
        val db = FirebaseFirestore.getInstance()
        val plantRef = db.collection(Constants.COLL_PLANTS)
        plantEntity.plantId?.let { id ->
            plantRef.document(id)
                .delete()
                .addOnFailureListener {
                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

}