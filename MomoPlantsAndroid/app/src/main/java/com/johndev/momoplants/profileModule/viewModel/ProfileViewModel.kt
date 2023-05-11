package com.johndev.momoplants.profileModule.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.profileModule.model.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel:ViewModel() {

    private val profileRepository = ProfileRepository()

    private val _context = MutableLiveData<Context?>()
    val context: LiveData<Context?> = _context

    private val _user = MutableLiveData<UserEntity?>()
    val user:LiveData<UserEntity?> = _user

    fun getUserById(context:Context){
        _context.value = context
        viewModelScope.launch(Dispatchers.IO) {
            val result = profileRepository.getUserById(readFromSharedPrefs())
            withContext(Dispatchers.Main) {
                _user.value = result
            }
        }
    }

    private val mainKeyAlias by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        MasterKeys.getOrCreate(keyGenParameterSpec)
    }

    private val sharedPreferences by lazy {
        val sharedPrefsFile = "sharedPrefs"
        EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            context.value!!,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun readFromSharedPrefs(): Long {
        return sharedPreferences.getLong(Constants.USER_ACTIVE, 0L)
    }


}