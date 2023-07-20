package com.johndev.momoplants.ui.profileModule.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.johndev.momoplants.R
import com.johndev.momoplants.ui.loginModule.view.LoginActivity
import com.johndev.momoplants.ui.profileModule.model.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {

    private var _userData = MutableLiveData<FirebaseUser?>()
    val userData: LiveData<FirebaseUser?> = _userData

    private var _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> = _imageUri

    fun onGetUserData() {
        _userData.value = FirebaseAuth.getInstance().currentUser
    }

    fun onSignInAccount(context: Context) {
        AuthUI.getInstance().signOut(context)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.menu_option_sign_out),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(context, intent, null)
            }
    }

    fun saveUriImage(uri: Uri) {
        profileRepository.onSaveUriImage(uri)
    }

    fun getUriImage() {
        profileRepository.onGetUriImage()?.let {
            _imageUri.value = it
        }
    }

}