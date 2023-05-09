package com.johndev.momoplants.common.utils

import com.johndev.momoplants.loginModule.viewModel.UserViewModel
import com.johndev.momoplants.profileModule.viewModel.ProfileViewModel

object ViewModels {

    private val InstanceUserViewModel: UserViewModel by lazy { UserViewModel() }
    fun getInstanceUserVM() = InstanceUserViewModel
    private val InstanceProfileViewModel:ProfileViewModel by lazy { ProfileViewModel() }
    fun getInstanceProfileVM() = InstanceProfileViewModel

}