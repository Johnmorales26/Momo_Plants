package com.johndev.momoplants.profileModule.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.profileModule.model.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel:ViewModel() {
    private val profileRepository = ProfileRepository()
    private val _user = MutableLiveData<UserEntity?>()
    val user:LiveData<UserEntity?> = _user

    fun getUserById(context:Context){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        viewModelScope.launch(Dispatchers.IO) {
            val user_id = sharedPreferences.getLong(Constants.USER_ACTIVE, 0)
            val result = profileRepository.getUserById(user_id)
            withContext(Dispatchers.Main){
                _user.value = result
            }

        }

    }
}