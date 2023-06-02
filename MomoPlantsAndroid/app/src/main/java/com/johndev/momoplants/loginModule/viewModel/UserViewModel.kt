package com.johndev.momoplants.loginModule.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.getMsgErrorByCode
import com.johndev.momoplants.loginModule.model.LoginRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel() : ViewModel() {

    private val repository = LoginRepository()

    private val _context = MutableLiveData<Context?>()
    val context: LiveData<Context?> = _context

    private val mainKeyAlias by lazy {
        // Although you can define your own key generation parameter specification, it's
        // recommended that you use the value specified here.
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        MasterKeys.getOrCreate(keyGenParameterSpec)
    }

    private val sharedPreferences by lazy {
        //The name of the file on disk will be this, plus the ".xml" extension.
        val sharedPrefsFile = "sharedPrefs"
        //Create the EncryptedSharedPremises using the key above
        EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            context.value!!,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun writeToSharedPrefs(context: Context, value: Long) {
        _context.value = context
        with (sharedPreferences.edit()) {
            putLong(Constants.USER_ACTIVE, value)
            apply()
        }
    }

    private val registeredUser = MutableLiveData<UserEntity?>()
    fun getRegisteredUser() = registeredUser

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    fun getUserByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            registeredUser.value = repository.getUserByEmailAndPassword(email, password)
        }
    }

    fun insert(userEntity: UserEntity, context: Context) {
        viewModelScope.launch {
            try {
                if (repository.insert(userEntity) < 0) {
                    snackbarMsg.value = R.string.login_insert_error
                } else {
                    getUserByEmailAndPassword(userEntity.email, userEntity.password)
                    writeToSharedPrefs(context, userEntity.user_id)
                    snackbarMsg.value = R.string.login_insert_success
                }
            } catch (e: Exception) {
                snackbarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }

}