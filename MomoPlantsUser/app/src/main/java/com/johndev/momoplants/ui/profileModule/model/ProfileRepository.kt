package com.johndev.momoplants.ui.profileModule.model

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import com.johndev.momoplants.common.utils.Constants.IMAGE_SP_URI
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun onSaveUriImage(uri: Uri) {
        sharedPreferences.edit {
            putString(IMAGE_SP_URI, uri.toString())
            apply()
        }
    }

    fun onGetUriImage(): String? = sharedPreferences.getString(IMAGE_SP_URI, null)

}