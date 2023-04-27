package com.johndev.momoplants.loginModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.getMsgErrorByCode
import com.johndev.momoplants.loginModule.model.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel : ViewModel() {

    private val repository = LoginRepository()

    private val registeredUser = MutableLiveData<UserEntity?>()
    fun getRegisteredUser() = registeredUser

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    fun getUserByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            registeredUser.value = repository.getUserByEmailAndPassword(email, password)
        }
    }

    fun insert(userEntity: UserEntity) {
        viewModelScope.launch {
            try {
                repository.insert(userEntity)
                getUserByEmailAndPassword(userEntity.email, userEntity.password)
                snackbarMsg.value = R.string.login_insert_success
            } catch (e: Exception) {
                snackbarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }

}