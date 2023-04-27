package com.johndev.momoplants.common.utils

import com.johndev.momoplants.loginModule.viewModel.UserViewModel

object ViewModels {

    private val InstanceUserViewModel: UserViewModel by lazy { UserViewModel() }
    fun getInstanceUserVM() = InstanceUserViewModel

}