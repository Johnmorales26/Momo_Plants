package com.johndev.momoplants.common.utils

import com.johndev.momoplants.profileModule.viewModel.ProfileViewModel

object ViewModels {

    private val InstanceProfileViewModel:ProfileViewModel by lazy { ProfileViewModel() }
    fun getInstanceProfileVM() = InstanceProfileViewModel

}