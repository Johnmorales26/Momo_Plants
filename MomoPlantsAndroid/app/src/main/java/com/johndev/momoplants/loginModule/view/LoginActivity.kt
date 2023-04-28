package com.johndev.momoplants.loginModule.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.loginModule.viewModel.UserViewModel
import com.johndev.momoplants.mainModule.view.MainActivity
import com.johndev.momoplants.onboardingModule.OnboardingStartFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setupViewModel()
        initFragment()
        verifyLoggedIn()
    }

    private fun verifyLoggedIn() {
        val isLogged = sharedPreferences.getBoolean(Constants.IS_SESSION_ACTIVE, false)
        if (isLogged) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun initFragment() {
        val signMainFragment = OnboardingStartFragment()
        openFragment(fragment = signMainFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
    }

    companion object {
        lateinit var userViewModel: UserViewModel
        lateinit var sharedPreferences: SharedPreferences
    }

}