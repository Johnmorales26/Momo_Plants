package com.johndev.momoplants.loginModule.model

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.common.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun saveToken(uid: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val token = preferences.getString(Constants.PROP_TOKEN, null)
        token?.let {
            val db = FirebaseFirestore.getInstance()
            val tokenMap = hashMapOf(Pair(Constants.PROP_TOKEN, token))
            db.collection(Constants.COLL_USERS)
                .document(uid)
                .collection(Constants.COLL_TOKENS)
                .add(tokenMap)
                .addOnSuccessListener {
                    Log.i("Register token", token)
                    preferences.edit {
                        putString(Constants.PROP_TOKEN, null)
                            .apply()
                    }
                }
                .addOnFailureListener {
                    Log.i("No register token", token)
                }
        }
    }
}