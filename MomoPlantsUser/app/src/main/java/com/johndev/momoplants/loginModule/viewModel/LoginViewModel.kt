package com.johndev.momoplants.loginModule.viewModel

import androidx.lifecycle.ViewModel
import com.johndev.momoplants.loginModule.model.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun saveToken(uid: String) {
        repository.saveToken(uid)
    }

}