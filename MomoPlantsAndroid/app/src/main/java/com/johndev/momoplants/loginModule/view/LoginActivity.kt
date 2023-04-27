package com.johndev.momoplants.loginModule.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.loginModule.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViewModel()
        initFragment()
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun initFragment() {
        val signMainFragment = SignMainFragment()
        openFragment(fragment = signMainFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
    }

    companion object {
        lateinit var userViewModel: UserViewModel
    }

}