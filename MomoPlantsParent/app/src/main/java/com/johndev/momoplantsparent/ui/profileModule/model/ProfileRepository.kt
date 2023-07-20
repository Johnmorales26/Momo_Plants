package com.johndev.momoplantsparent.ui.profileModule.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.utils.Constants.IMAGE_SP_URI
import com.johndev.momoplantsparent.ui.loginModule.view.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun onSaveUriImage(uri: Uri) {
        sharedPreferences.edit {
            putString(IMAGE_SP_URI, uri.toString())
            apply()
        }
    }

    fun onGetUriImage(): String? = sharedPreferences.getString(IMAGE_SP_URI, null)

    fun signInAccount() {
        AuthUI.getInstance().signOut(context)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.menu_option_sign_out),
                    Toast.LENGTH_SHORT
                ).show()
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                    param(FirebaseAnalytics.Param.SUCCESS, 100)
                    param(FirebaseAnalytics.Param.METHOD, "sign_out")
                }
                val intent = Intent(context, LoginActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
            }
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.SUCCESS, 201)
                        param(FirebaseAnalytics.Param.METHOD, "sign_out")
                    }
                }
            }
    }

}