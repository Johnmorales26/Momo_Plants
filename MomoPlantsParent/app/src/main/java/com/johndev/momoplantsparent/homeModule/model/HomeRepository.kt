package com.johndev.momoplantsparent.homeModule.model

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.PlantEntity
import com.johndev.momoplantsparent.common.utils.Constants
import com.johndev.momoplantsparent.common.utils.EventPost
import com.johndev.momoplantsparent.common.utils.FirebaseUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val database: FirebaseUtils
) {

    private lateinit var firestoreListener: ListenerRegistration

    fun onUploadImage(
        plantId: String?,
        photoSelectedUri: Uri?,
        callback: (EventPost) -> Unit,
        onProgressListener: (Int) -> Unit,
    ) {
        val eventPost = EventPost()
        eventPost.documentId = plantId ?: database.getPlantsRef().document().id
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
                    Toast.makeText(context,
                        context.getString(R.string.error_upload_image), Toast.LENGTH_SHORT).show()
                    eventPost.isSuccess = false
                    callback(eventPost)
                }
        }
    }

    fun configFirestoreRealtime(
        onError: (Int) -> Unit,
        onAdded: (PlantEntity) -> Unit,
        onModified: (PlantEntity) -> Unit,
        onRemoved: (PlantEntity) -> Unit
    ) {
        val plantRef = database.getPlantsRef()
        firestoreListener = plantRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                onError(R.string.msg_query_error)
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val plant = snapshot.document.toObject(PlantEntity::class.java)
                plant.plantId = snapshot.document.id
                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> onAdded(plant)
                    DocumentChange.Type.MODIFIED -> onModified(plant)
                    DocumentChange.Type.REMOVED -> onRemoved(plant)
                }
            }
        }
    }

    fun onSave(
        plantEntity: PlantEntity,
        documentId: String,
        onSuccess: (Int) -> Unit,
        onFailure: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        database.getPlantsRef()
            .document(documentId)
            .set(plantEntity)
            .addOnSuccessListener {
                onSuccess(R.string.msg_plant_added)
            }
            .addOnFailureListener {
                onFailure(R.string.msg_error_add_plant)
            }
            .addOnCompleteListener { onComplete() }
    }

    fun onUpdate(
        plantEntity: PlantEntity,
        onSuccess: (Int) -> Unit,
        onFailure: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        plantEntity.plantId?.let { id ->
            database.getPlantsRef()
                .document(id)
                .set(plantEntity)
                .addOnSuccessListener {
                    onSuccess(R.string.msg_plant_update)
                }
                .addOnFailureListener {
                    onFailure(R.string.msg_error_update_plant)
                }
                .addOnCompleteListener { onComplete() }
        }
    }

    fun onDelete(
        plantEntity: PlantEntity,
        onFailure: (Int) -> Unit
    ) {
        val plantRef = database.getPlantsRef()
        plantEntity.plantId?.let { id ->
            plantRef.document(id)
                .delete()
                .addOnFailureListener {
                    onFailure(R.string.msg_error_delete_plant)
                }
        }
    }

    fun onRemoveListener() = firestoreListener.remove()

}