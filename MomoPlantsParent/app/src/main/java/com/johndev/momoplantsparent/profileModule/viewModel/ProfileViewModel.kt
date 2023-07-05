package com.johndev.momoplantsparent.profileModule.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.johndev.momoplantsparent.profileModule.model.ProfileRepository
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

    fun signInAccount(context: Context) {
        profileRepository.signInAccount()
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