package com.johndev.momoplants.ui.loginModule.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.DownloadController
import com.johndev.momoplants.common.utils.checkSelfPermissionCompat
import com.johndev.momoplants.common.utils.printErrorToast
import com.johndev.momoplants.common.utils.printInfoToast
import com.johndev.momoplants.common.utils.printSuccessToast
import com.johndev.momoplants.common.utils.requestPermissionsCompat
import com.johndev.momoplants.common.utils.shouldShowRequestPermissionRationaleCompat
import com.johndev.momoplants.databinding.ActivityLoginBinding
import com.johndev.momoplants.ui.loginModule.viewModel.LoginViewModel
import com.johndev.momoplants.ui.mainModule.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)

            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    printSuccessToast(msgText = getString(R.string.login_welcome, user.displayName), context = this)
                    loginViewModel.saveToken(user.uid)
                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                if (response == null) {
                    printInfoToast(R.string.login_see_you_soon, context = this)
                    finish()
                } else {
                    response.error?.let { firebaseException ->
                        if (firebaseException.errorCode == ErrorCodes.NO_NETWORK) {
                            printErrorToast(R.string.login_no_net, context = this)
                        } else {
                            printErrorToast(msgText = getString(
                                R.string.login_error_code,
                                firebaseException.errorCode.toString()
                            ), context = this)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        configAuth()
    }

    private fun setupViewModel() {
        val vm: LoginViewModel by viewModels()
        loginViewModel = vm
    }

    private fun configAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.PhoneBuilder().build()
                )

                val loginView = AuthMethodPickerLayout.Builder(R.layout.view_login)
                    .setEmailButtonId(R.id.fabEmail)
                    .setGoogleButtonId(R.id.fabGoogle)
                    .setPhoneButtonId(R.id.fabPhone)
                    .setFacebookButtonId(R.id.fabFacebook)
                    .build()

                authLauncher.launch(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setAuthMethodPickerLayout(loginView)
                        .setTheme(R.style.FirebaseUITheme)
                        .build()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

}